package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-06-19 19:10:27
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    ResponseResult selectRoutersByUserId(Long userId);

    ResponseResult listInOrder(String status, String menuName);

    ResponseResult searchById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(List<Long> menuId);

    ResponseResult selectTree();

    ResponseResult selectRoleMenuTree(Long id);
}

