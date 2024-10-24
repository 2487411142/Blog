package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.RoleDto;
import com.limemartini.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-06-19 19:41:42
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long userId);

    ResponseResult listRoleInPages(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(Role role);

    ResponseResult addNewRole(RoleDto dto);

    ResponseResult selectRoleById(Long id);

    ResponseResult updateRole(RoleDto dto);

    ResponseResult listAllRole();

    ResponseResult deleteRole(List<Long> id);
}

