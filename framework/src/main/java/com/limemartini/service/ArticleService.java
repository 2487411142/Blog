package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.BackgroundArticleDto;
import com.limemartini.domain.dto.FullArticleAndTagDto;
import com.limemartini.domain.entity.Article;
import com.limemartini.domain.vo.ArticleDetailVo;
import com.limemartini.domain.vo.FullArticleAndTagVo;
import com.limemartini.domain.vo.HotArticleVo;
import com.limemartini.domain.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {
    List<HotArticleVo> hotArticleList();

    PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVo articleDetail(Long id);

    Map<String, Integer> getViewCountMap();

    void updateViewCount(Long id);

    void addNewArticle(BackgroundArticleDto articleDto);

    PageVo listInPages(Integer pageNum, Integer pageSize, String title, String summary);

    FullArticleAndTagVo searchById(Integer id);

    void updateArticle(FullArticleAndTagDto dto);

    ResponseResult deleteArticle(List<Long> ids);
}
