package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.domain.ResponseResult;
import com.limemartini.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Tag(name = "article interface", description = "manipulating articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /*@GetMapping("/list")
    public String test(){
        return articleService.list().toString();
    }*/

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "get hot articles")
    public ResponseResult hotArticle(){
        return ResponseResult.okResult(articleService.hotArticleList());
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "get articles of certain category in pages")
    @Parameters(
            @Parameter(name = "pageNum", description = "page number")
    )
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return ResponseResult.okResult(articleService.articleList(pageNum, pageSize, categoryId));
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "get details of an article")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return ResponseResult.okResult(articleService.articleDetail(id));
    }

    @PutMapping("updateViewCount/{id}")
    @SystemLog(businessName = "get viewcount of the article")
    public ResponseResult updateViewCount(@PathVariable Long id){
        articleService.updateViewCount(id);
        return ResponseResult.okResult();
    }
}
