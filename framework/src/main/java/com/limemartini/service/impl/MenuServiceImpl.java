package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Menu;
import com.limemartini.domain.entity.RoleMenu;
import com.limemartini.domain.vo.MenuVoShorted;
import com.limemartini.domain.vo.MenuVo;
import com.limemartini.domain.vo.MenusAndCheckedKeysVo;
import com.limemartini.domain.vo.RoutersVo;
import com.limemartini.mapper.MenuMapper;
import com.limemartini.service.MenuService;
import com.limemartini.service.RoleMenuService;
import com.limemartini.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-06-19 19:10:27
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        if (userId == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> keys = menus
                    .stream()
                    .map(Menu::getPerms)
                    .toList();
            return keys;
        }

        return getBaseMapper().selectPermsByUserId(userId);

    }

    @Override
    public ResponseResult selectRoutersByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus;
        if (userId.equals(1L)) {
            menus = menuMapper.selectAllMenus();
        } else {
            menus = menuMapper.selectMenusByUserId(userId);
        }
        List<Menu> tree = buildMenuTree(menus, 0L);
        return ResponseResult.okResult(new RoutersVo(tree));
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> tree = menus
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(buildMenuTree(menus, menu.getId())))
                .toList();
        return tree;
    }

    @Override
    public ResponseResult listInOrder(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(status)) {
            queryWrapper.eq(Menu::getStatus, status);
        }
        if (StringUtils.hasText(menuName)) {
            queryWrapper.eq(Menu::getMenuName, menuName);
        }
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuVo> menuVoList = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVoList);
    }

    @Override
    public ResponseResult searchById(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (Objects.equals(menu.getParentId(), menu.getId())) {
            return ResponseResult.errorResult(500, "cannot set parent menu as itself");
        }
        updateMenu(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(List<Long> menuIds) {
        for (Long menuId : menuIds) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getParentId, menuId);
            List<Menu> children = list(queryWrapper);
            if (!children.isEmpty()) {
                return ResponseResult.errorResult(500, "cannot delete a menu that has children menu");
            }
            removeById(menuId);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectTree() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        List<MenuVoShorted> menuVoShortedList = BeanCopyUtils.copyBeanList(getMenusInOrder(queryWrapper), MenuVoShorted.class);
        List<MenuVoShorted> tree = buildShortedVoTree(menuVoShortedList, 0L);
        return ResponseResult.okResult(tree);
    }

    @Override
    public ResponseResult selectRoleMenuTree(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        List<Menu> tree = buildMenuTree(getMenusInOrder(queryWrapper), 0L);

        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!id.equals(SystemConstants.SUPER_ADMIN_ID)) {
            roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId, id);
        }
        List<RoleMenu> roleMenuList = roleMenuService.list(roleMenuLambdaQueryWrapper);

        List<Long> checkedKeys = roleMenuList.stream()
                .map(RoleMenu::getMenuId)
                .toList();
        return ResponseResult.okResult(new MenusAndCheckedKeysVo(tree, checkedKeys));
    }

    private List<Menu> getMenusInOrder(LambdaQueryWrapper<Menu> queryWrapper){
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        return list(queryWrapper);
    }


    private List<MenuVoShorted> buildShortedVoTree(List<MenuVoShorted> voList, Long parentId) {
        List<MenuVoShorted> tree = voList
                .stream()
                .filter(vo -> vo.getParentId().equals(parentId))
                .map(vo -> vo.setChildren(buildShortedVoTree(voList, vo.getId())))
                .toList();
        return tree;
    }

}
