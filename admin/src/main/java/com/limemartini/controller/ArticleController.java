package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.BackgroundArticleDto;
import com.limemartini.domain.dto.FullArticleAndTagDto;
import com.limemartini.domain.vo.FullArticleAndTagVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addNewArticle(@RequestBody BackgroundArticleDto articleDto){
        articleService.addNewArticle(articleDto);
        return ResponseResult.okResult();
    }

    @GetMapping("/list")
    public ResponseResult listArticlesInPages(Integer pageNum, Integer pageSize, String title, String summary){
        PageVo pageVo = articleService.listInPages(pageNum, pageSize, title, summary);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping("/{id}")
    public ResponseResult searchArticleById(@PathVariable Integer id){
        FullArticleAndTagVo vo = articleService.searchById(id);
        return ResponseResult.okResult(vo);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody FullArticleAndTagDto dto){
        articleService.updateArticle(dto);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteArticle(@PathVariable List<Long> ids){
        return articleService.deleteArticle(ids);
    }
}
