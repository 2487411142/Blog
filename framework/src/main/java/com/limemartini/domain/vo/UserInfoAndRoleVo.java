package com.limemartini.domain.vo;

import com.limemartini.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleVo {

    private List<Long> roleIds;

    private List<Role> roles;

    private BackgroundUserInfoVo user;
}
