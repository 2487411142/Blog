package com.limemartini.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Article;
import com.limemartini.domain.entity.Category;
import com.limemartini.domain.vo.CategoryVo;
import com.limemartini.domain.vo.ExcelCategoryVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.domain.vo.SimpleCategoryVo;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.mapper.CategoryMapper;
import com.limemartini.service.ArticleService;
import com.limemartini.service.CategoryService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import com.limemartini.utils.WebUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-06-06 21:05:25
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<SimpleCategoryVo> getCategoryList() {
        // search for articles of which status = 0
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleWrapper);
        // get category_id, remove duplicate
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // search category table, get category name
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> category.getStatus().equals(SystemConstants.CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());

        return BeanCopyUtils.copyBeanList(categories, SimpleCategoryVo.class);
    }

    @Override
    public List<CategoryVo> listAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        queryWrapper.select(Category::getDescription, Category::getId, Category::getName);
        List<Category> categories = list(queryWrapper);
        return BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            WebUtils.setDownLoadHeader("Categories", response);

            List<Category> categories = list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);

            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @Override
    public PageVo listInPages(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)){
            queryWrapper.eq(Category::getName, name);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(Category::getStatus, status);
        }
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Category> categories = page.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return new PageVo(categoryVos, page.getTotal());
    }

    @Override
    public ResponseResult addNewCategory(Category category) {
        Long userId = SecurityUtils.getUserId();
        category.setCreateBy(userId);
        category.setUpdateBy(userId);

        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult searchById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        category.setUpdateBy(SecurityUtils.getUserId());
        category.setUpdateTime(new Date());
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeCategory(List<Long> ids) {
        ids.forEach(this::removeById);
        return ResponseResult.okResult();
    }
}
