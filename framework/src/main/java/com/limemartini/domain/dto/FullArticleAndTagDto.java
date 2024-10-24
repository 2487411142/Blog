package com.limemartini.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullArticleAndTagDto {
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    private String thumbnail;

    //isTop（0 false，1 true）
    private String isTop;

    //status（0 normal，1 draft）
    private String status;

    private Long viewCount;

    //whether comment is allowed (0 false，1 true)
    private String isComment;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    //0 not deleted，1 deleted）
    private Integer delFlag;

    private List<Long> tags;
}
