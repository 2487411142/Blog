package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.UserAdditionDto;
import com.limemartini.domain.dto.UserModificationDto;
import com.limemartini.domain.entity.User;
import com.limemartini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult listUsersInPages(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status){
        return userService.listUsersInPages(pageNum, pageSize, userName, phonenumber, status);
    }

    @PostMapping
    public ResponseResult addNewUser(@RequestBody UserAdditionDto dto){
        return userService.addNewUser(dto);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteUser(@PathVariable List<Long> ids){
        return userService.removeUser(ids);
    }

    @GetMapping("/{id}")
    public ResponseResult displayUserAndRole(@PathVariable Long id){
        return userService.displayUserInfoAndRole(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserModificationDto dto){
        return userService.updateUser(dto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeUserStatus(@RequestBody User user){
        return userService.changeUserStatus(user);
    }
}
