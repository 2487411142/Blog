package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Comment;
import com.limemartini.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "get a list of comments of articles")
    public ResponseResult articleCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return ResponseResult.okResult(commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize));
    }

    @PostMapping
    @SystemLog(businessName = "add comments")
    public ResponseResult addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return ResponseResult.okResult();
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "get a list of comments of links")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return ResponseResult.okResult(commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize));
    }
}
