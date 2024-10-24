package com.limemartini.service.impl;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.LoginUser;
import com.limemartini.domain.entity.User;
import com.limemartini.service.AdminLoginService;
import com.limemartini.utils.JwtUtil;
import com.limemartini.utils.RedisCache;
import com.limemartini.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // check if authentication is passed
        if (authenticate == null){
            throw new RuntimeException("username or password is invalid");
        }
        // get userid, generate token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // save user info into redis
        redisCache.setCacheObject("adminlogin:"+userId, loginUser );
        // encapsulate
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long id = SecurityUtils.getUserId();
        redisCache.deleteObject("adminlogin:"+id.toString());
        return ResponseResult.okResult();
    }
}
