package com.limemartini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundArticleVo {
    private Long id;

    private String title;

    private String summary;

    private Long categoryId;

    private String thumbnail;

    private String content;

    private Long viewCount;

    private Date createTime;

    //isTop（0 false，1 true）
    private String isTop;

    //status（0 normal，1 draft）
    private String status;

    //whether comment is allowed (0 false，1 true)
    private String isComment;
}
