package com.limemartini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;

    private Long articleId;

    private List<CommentVo> children;

    // id of root comment
    private Long rootId;

    private String content;

    // The userid of the target comment being replied to
    private Long toCommentUserId;

    private String toCommentUserName;

    // The comment id of the target comment being replied to
    private Long toCommentId;

    private Long createBy;

    private String username;

    private Date createTime;
}
