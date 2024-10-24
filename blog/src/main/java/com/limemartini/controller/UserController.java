package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.User;
import com.limemartini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "get user info")
    public ResponseResult userInfo() {
        return ResponseResult.okResult(userService.userInfo());
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "update user info")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ResponseResult.okResult();
    }

    @PostMapping("/register")
    @SystemLog(businessName = "register a new user")
    public ResponseResult register(@RequestBody User user) {
        userService.register(user);
        return ResponseResult.okResult();
    }
}
