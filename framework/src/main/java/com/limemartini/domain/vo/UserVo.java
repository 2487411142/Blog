package com.limemartini.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Long id;

    private String nickName;

    private String email;

    private String sex;

    private String avatar;
}
