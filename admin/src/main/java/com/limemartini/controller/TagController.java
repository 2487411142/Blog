package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.TagListDto;
import com.limemartini.domain.entity.Tag;
import com.limemartini.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult listTagsInPages(Integer pageNum, Integer pageSize, TagListDto dto){
        return ResponseResult.okResult(tagService.listInPage(pageNum, pageSize, dto));
    }

    @PostMapping
    public ResponseResult addNewTag(@RequestBody Tag tag){
        tagService.addNewTag(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable List<Long> id){

        return tagService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Integer id){
        return ResponseResult.okResult(tagService.get(id.longValue()));
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        tagService.modify(tag);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return ResponseResult.okResult(tagService.listAll());
    }
}
