package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.RoleDto;
import com.limemartini.domain.entity.Role;
import com.limemartini.domain.entity.RoleMenu;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.domain.vo.RoleVo;
import com.limemartini.mapper.RoleMapper;
import com.limemartini.service.RoleMenuService;
import com.limemartini.service.RoleService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 19:41:42
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeysByUserId(Long userId) {
        if (userId == 1L){
            List<String> keys = new ArrayList<>();
            keys.add("admin");
            return keys;
        }
        return getBaseMapper().selectRoleKeysByUserId(userId);
    }

    @Override
    public ResponseResult listRoleInPages(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(roleName)){
            queryWrapper.like(Role::getRoleName, roleName);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(Role::getStatus, status);
        }
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        return ResponseResult.okResult(new PageVo(roleVos, page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(Role role) {
        role.setUpdateBy(SecurityUtils.getUserId());
        role.setUpdateTime(new Date());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addNewRole(RoleDto dto) {
        Role roleToAdd = BeanCopyUtils.copyBean(dto, Role.class);
        Date now = new Date();
        roleToAdd.setUpdateTime(now);
        roleToAdd.setCreateTime(now);
        roleToAdd.setUpdateBy(SecurityUtils.getUserId());
        roleToAdd.setCreateBy(SecurityUtils.getUserId());
        save(roleToAdd);

        Long roleId = roleToAdd.getId();
        List<RoleMenu> roleMenus = dto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(roleId, menuId))
                .toList();
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectRoleById(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role, RoleVo.class));
    }

    @Override
    public ResponseResult updateRole(RoleDto dto) {
        // update role
        Role roleToUpdate = BeanCopyUtils.copyBean(dto, Role.class);
        updateById(roleToUpdate);

        // update role menu db
        // delete all role-menu relationship
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleToUpdate.getId());
        roleMenuService.remove(queryWrapper);

        // save new role-menu relationship
        List<RoleMenu> newRelations = dto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(roleToUpdate.getId(), menuId))
                .toList();
        roleMenuService.saveBatch(newRelations);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = list(queryWrapper);
        return ResponseResult.okResult(roles);
    }

    @Override
    public ResponseResult deleteRole(List<Long> ids) {
        ids.forEach(this::removeById);
        return ResponseResult.okResult();
    }
}
