package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.RoleDto;
import com.limemartini.domain.entity.Role;
import com.limemartini.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listRoleInPages(Integer pageNum, Integer pageSize, String roleName, String status){
        return roleService.listRoleInPages(pageNum, pageSize, roleName, status);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role){
        return roleService.changeStatus(role);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

    @PostMapping
    public ResponseResult addNewRole(@RequestBody RoleDto dto){
        return roleService.addNewRole(dto);
    }

    @GetMapping("/{id}")
    public ResponseResult selectRoleById(@PathVariable Long id){
        return roleService.selectRoleById(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleDto dto){
        return roleService.updateRole(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable List<Long> id){
        return roleService.deleteRole(id);
    }



}
