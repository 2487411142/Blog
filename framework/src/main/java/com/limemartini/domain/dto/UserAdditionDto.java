package com.limemartini.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdditionDto {
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

    private List<Long> roleIds;
}
