package com.limemartini.domain.entity;

import java.util.Date;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2024-06-19 19:41:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role {

    //@TableId
    private Long id;

    private String roleName;

    // role permission key
    private String roleKey;

    // display order
    private Integer roleSort;

    //（0 normal 1 frozen）
    private String status;

    private String delFlag;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private String remark;

}

