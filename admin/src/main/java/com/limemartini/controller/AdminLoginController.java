package com.limemartini.controller;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Menu;
import com.limemartini.domain.entity.User;
import com.limemartini.domain.vo.AdminUserInfoVo;
import com.limemartini.domain.vo.RoutersVo;
import com.limemartini.domain.vo.UserInfoVo;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.exception.SystemException;
import com.limemartini.service.AdminLoginService;
import com.limemartini.service.MenuService;
import com.limemartini.service.RoleService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            // raise exception
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        // get current user's id from security context holder
        Long userId = SecurityUtils.getUserId();
        // get menu info by id
        List<String> permissions = menuService.selectPermsByUserId(userId);
        // get role by id
        List<String> roleKeyList =roleService.selectRoleKeysByUserId(userId);
        // get user info
        User user = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // encapsulate
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        return menuService.selectRoutersByUserId(userId);
    }
}
