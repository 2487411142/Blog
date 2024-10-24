package com.limemartini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {

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

    private String remark;
}
