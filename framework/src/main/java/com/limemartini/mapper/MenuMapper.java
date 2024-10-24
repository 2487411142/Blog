package com.limemartini.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.limemartini.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-19 19:10:25
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllMenus();

    List<Menu> selectMenusByUserId(Long userId);
}
