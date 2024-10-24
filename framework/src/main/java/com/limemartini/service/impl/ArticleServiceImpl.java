package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.BackgroundArticleDto;
import com.limemartini.domain.dto.FullArticleAndTagDto;
import com.limemartini.domain.entity.Article;
import com.limemartini.domain.entity.ArticleTag;
import com.limemartini.domain.entity.Category;
import com.limemartini.domain.vo.*;
import com.limemartini.mapper.ArticleMapper;
import com.limemartini.mapper.ArticleTagMapper;
import com.limemartini.mapper.CategoryMapper;
import com.limemartini.service.ArticleService;
import com.limemartini.service.ArticleTagService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<HotArticleVo> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // must be published article
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // sort by view counts decs
        queryWrapper.orderByDesc(Article::getViewCount);
        // at most 10 articles
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // bean copy
        /*List<HotArticleVo> hotArticleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article, vo);
            hotArticleVos.add(vo);
        }*/
        // get view count from redis
        articles.forEach(this::getViewCountFromRedis);

        return BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
    }

    @Override
    public PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // conditional query
        // 1. category id (if exist)
        queryWrapper.eq(categoryId!=null&&categoryId>0, Article::getCategoryId, categoryId);
        // 2. status = 0
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 3. sort by isTop
        queryWrapper.orderByDesc(Article::getIsTop);

        // page
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, queryWrapper);
        List<Article> articles = articlePage.getRecords();

        // get view count from redis
        articles.forEach(this::getViewCountFromRedis);

        // query category name
        articles.forEach(article -> article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName()));

        //encapsulate
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);

        return new PageVo(articleListVos, articlePage.getTotal());
    }

    @Override
    public ArticleDetailVo articleDetail(Long id) {
        // search article by id
        Article article = getById(id);
        // get view count from redis
        getViewCountFromRedis(article);
        // copy into vo
        ArticleDetailVo articleDetailVo= BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // search category name by category id
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        // encapsulate
        return articleDetailVo;
    }

    private void getViewCountFromRedis(Article article) {
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, article.getId().toString());
        article.setViewCount(viewCount.longValue());
    }

    @Override
    public Map<String, Integer> getViewCountMap() {
        List<Article> articles = list();
        return articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
    }

    @Override
    public void updateViewCount(Long id) {
        // update view count saved in redis
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, id.toString(), 1);
    }

    @Override
    public void addNewArticle(BackgroundArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        Long articleId = article.getId();
        List<ArticleTag> articleTagList = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleId, tagId))
                .toList();
        articleTagService.saveBatch(articleTagList);
    }

    @Override
    public PageVo listInPages(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)){
            queryWrapper.like(Article::getTitle, title);
        }
        if (StringUtils.hasText(summary)){
            queryWrapper.like(Article::getSummary, summary);
        }
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<BackgroundArticleVo> backgroundArticleVos = BeanCopyUtils.copyBeanList(page.getRecords(), BackgroundArticleVo.class);
        return new PageVo(backgroundArticleVos, page.getTotal());
    }

    @Override
    public FullArticleAndTagVo searchById(Integer id) {
        Article article = getById(id.longValue());
        Long articleId = article.getId();

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);
        List<Long> tags = articleTagList.stream()
                .map(ArticleTag::getTagId)
                .toList();
        FullArticleAndTagVo vo = BeanCopyUtils.copyBean(article, FullArticleAndTagVo.class);
        vo.setTags(tags);
        return vo;
    }

    @Override
    public void updateArticle(FullArticleAndTagDto dto) {
        Article article = BeanCopyUtils.copyBean(dto, Article.class);
        updateById(article);

        Long articleId = article.getId();
        List<ArticleTag> newList = dto.getTags().stream()
                .map(tagId -> new ArticleTag(articleId, tagId))
                .toList();

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> old = articleTagService.list(queryWrapper);

        List<ArticleTag> toRemove = new ArrayList<>(old);
        toRemove.removeAll(newList);
        for (ArticleTag articleTag : toRemove){
            LambdaQueryWrapper<ArticleTag> qw = new LambdaQueryWrapper<>();
            qw.eq(ArticleTag::getArticleId, articleTag.getArticleId());
            qw.eq(ArticleTag::getTagId, articleTag.getTagId());
            articleTagService.remove(qw);
        }

        List<ArticleTag> toAdd = new ArrayList<>(newList);
        toAdd.removeAll(old);
        for (ArticleTag articleTag : toAdd){
            articleTagService.save(articleTag);
        }

    }

    @Override
    public ResponseResult deleteArticle(List<Long> ids) {
        ids.forEach(this::removeById);
        return ResponseResult.okResult();
    }


}
