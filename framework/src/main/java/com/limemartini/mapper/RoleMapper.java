package com.limemartini.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.limemartini.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-19 19:41:41
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(Long userId);
}
