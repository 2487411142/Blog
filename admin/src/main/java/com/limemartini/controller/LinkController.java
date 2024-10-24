package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Link;
import com.limemartini.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listLinksInPages(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.listLinksInPages(pageNum, pageSize, name, status);
    }

    @PostMapping
    public ResponseResult addNewLink(@RequestBody Link link){
        return linkService.addNewLink(link);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable List<Long> id){
        return linkService.deleteLink(id);
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        return linkService.changeLinkStatus(link);
    }
}
