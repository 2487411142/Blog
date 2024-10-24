package com.limemartini.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2024-06-11 20:50:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("link")
public class Link  {
@TableId
    private Long id;

    private String name;

    private String logo;

    private String description;

    private String address;

    // status (0 check passed，1 check failed, 2 not checked)
    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    // is deleted（0 false，1 true）
    private Integer delFlag;
}

