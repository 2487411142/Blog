package com.limemartini.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 菜单权限表(Menu)表实体类
 *
 * @author makejava
 * @since 2024-06-19 19:10:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu {
    //@TableId
    private Long id;

    private String menuName;

    //parent menu ID
    private Long parentId;

    // order
    private Integer orderNum;

    // router path
    private String path;

    // component path
    private String component;

    // whether it's a foreign link
    private Integer isFrame;

    //menu type（M category, C menu, F button）
    private String menuType;

    //（0 visible 1 invisible）
    private String visible;

    //menu status（0 normal 1 frozen）
    private String status;

    // permission sign
    private String perms;

    private String icon;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private String remark;

    private String delFlag;

    @TableField(exist = false)
    private List<Menu> children;
}

