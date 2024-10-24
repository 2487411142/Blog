package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.User;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.exception.SystemException;
import com.limemartini.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            // raise exception
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
