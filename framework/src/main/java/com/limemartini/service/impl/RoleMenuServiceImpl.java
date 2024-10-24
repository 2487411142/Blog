package com.limemartini.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.domain.entity.RoleMenu;
import com.limemartini.mapper.RoleMenuMapper;
import com.limemartini.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-06-30 22:05:30
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
