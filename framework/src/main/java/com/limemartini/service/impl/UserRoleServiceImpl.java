package com.limemartini.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.domain.entity.UserRole;
import com.limemartini.mapper.UserRoleMapper;
import com.limemartini.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 19:27:15
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
