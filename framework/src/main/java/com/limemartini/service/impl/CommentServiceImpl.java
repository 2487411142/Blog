package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.entity.Comment;
import com.limemartini.domain.vo.CommentVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.exception.SystemException;
import com.limemartini.mapper.CommentMapper;
import com.limemartini.mapper.UserMapper;
import com.limemartini.service.CommentService;
import com.limemartini.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-06-13 19:30:58
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageVo commentList(String type, Long articleId, Integer pageNum, Integer pageSize) {
        // search for root comment of certain article
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // article id
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(type), Comment::getArticleId, articleId);
        // comment type
        queryWrapper.eq(Comment::getType, type);
        // root comment
        queryWrapper.eq(Comment::getRootId, -1);

        // pagination
        Page<Comment> page = new Page<Comment>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        // add children comments to root comments
        commentVoList.forEach(commentVo -> {
            // search all children comment of this root comment
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });

        return new PageVo(commentVoList, page.getTotal());
    }

    /**
     * search all children comment of the root comment
     * @param rootId id of root comment
     * @return list of CommentVo
     */
    private List<CommentVo> getChildren(Long rootId){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, rootId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> childrenCommentList = list(queryWrapper);

        return toCommentVoList(childrenCommentList);
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // loop voList
        voList
                .forEach(commentVo -> {
                    // add username by using createBy
                    String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    // add toCommentUserName by using toCommentUserId if toCommentUserId != -1;
                    if (commentVo.getToCommentUserId() != -1){
                        String toCommentUserName = userMapper.selectById(commentVo.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(toCommentUserName);
                    }
                });

        return voList;
    }


    @Override
    public void addComment(Comment comment) {
        // content not null
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
    }



}
