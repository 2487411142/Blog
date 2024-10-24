package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Menu;
import com.limemartini.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(String status, String menuName){
        return menuService.listInOrder(status, menuName);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult searchById(@PathVariable Long id){
        return menuService.searchById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuIds}")
    public ResponseResult delete(@PathVariable List<Long> menuIds){
        return menuService.deleteMenu(menuIds);
    }

    @GetMapping("/selectTree")
    public ResponseResult selectTree(){
        return menuService.selectTree();
    }

    @GetMapping("/selectRoleMenuTree/{id}")
    public ResponseResult selectRoleMenuTree(@PathVariable Long id){
        return menuService.selectRoleMenuTree(id);
    }

}
