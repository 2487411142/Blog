package com.limemartini.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String roleName;

    // role permission key
    private String roleKey;

    // display order
    private Integer roleSort;

    //（0 normal 1 frozen）
    private String status;

    private String remark;

    private List<Long> menuIds;
}
