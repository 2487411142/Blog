package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.entity.Comment;
import com.limemartini.domain.vo.PageVo;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2024-06-13 19:30:58
 */
public interface CommentService extends IService<Comment> {

    PageVo commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    void addComment(Comment comment);

}

