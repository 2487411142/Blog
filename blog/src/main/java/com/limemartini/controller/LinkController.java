package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.domain.ResponseResult;
import com.limemartini.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "get all links")
    public ResponseResult getAllLink(){
        return ResponseResult.okResult(linkService.getAllLink());
    }
}
