package com.limemartini.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2024-06-11 21:29:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User {
    //prime key
    private Long id;

    private String userName;

    private String nickName;

    private String password;

    // user type：0 normal，1 admin
    private String type;

    // status（0 normal, 1 frozen）
    private String status;

    private String email;

    private String phonenumber;

    //gender（0 male，1 female，2 unknown）
    private String sex;

    private String avatar;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    // is deleted（0 not deleted，1 deleted）
    private Integer delFlag;
}

