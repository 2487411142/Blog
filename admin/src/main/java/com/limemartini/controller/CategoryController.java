package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Category;
import com.limemartini.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAll(){
        return ResponseResult.okResult(categoryService.listAll());
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status){
        return ResponseResult.okResult(categoryService.listInPages(pageNum, pageSize, name, status));
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        categoryService.export(response);
    }

    @PostMapping
    public ResponseResult addNewCategory(@RequestBody Category category){
        return categoryService.addNewCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseResult searchCategoryById(@PathVariable Long id){
        return categoryService.searchById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteCategory(@PathVariable List<Long> ids){
        return categoryService.removeCategory(ids);
    }
}
