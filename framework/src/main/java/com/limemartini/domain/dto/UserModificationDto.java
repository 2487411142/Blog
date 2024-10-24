package com.limemartini.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModificationDto {

    private Long id;

    private String nickName;

    private String Username;

    private String email;

    private String sex;

    private String status;

    private List<Long> roleIds;
}
