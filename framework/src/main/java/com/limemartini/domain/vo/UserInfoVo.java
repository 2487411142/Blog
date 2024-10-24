package com.limemartini.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfoVo {

    private Long id;

    private String nickName;

    private String avatar;

    private String sex;

    private String email;

    private String phonenumber;

    private String userName;

    private String status;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

}
