package com.limemartini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {

    private Long id;

    private String roleName;

    // role permission key
    private String roleKey;

    // display order
    private Integer roleSort;

    private String remark;

    //（0 normal 1 frozen）
    private String status;
}
