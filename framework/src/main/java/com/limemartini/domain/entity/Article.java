package com.limemartini.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 *
 * @author makejava
 * @since 2024-06-02 21:01:52
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class Article {

    @TableId
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private String thumbnail;

    //isTop（0 false，1 true）
    private String isTop;

    //status（0 normal，1 draft）
    private String status;

    private Long viewCount;

    //whether comment is allowed (0 false，1 true)
    private String isComment;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //0 not deleted，1 deleted）
    private Integer delFlag;

    public Article(Long id, Long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}


