package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Category;
import com.limemartini.domain.vo.CategoryVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.domain.vo.SimpleCategoryVo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-06-06 21:05:25
 */
public interface CategoryService extends IService<Category> {

    List<SimpleCategoryVo> getCategoryList();

    List<CategoryVo> listAll();

    void export(HttpServletResponse response);

    PageVo listInPages(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addNewCategory(Category category);

    ResponseResult searchById(Long id);

    ResponseResult updateCategory(Category category);

    ResponseResult removeCategory(List<Long> ids);
}

