package com.limemartini.service.impl;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.LoginUser;
import com.limemartini.domain.entity.User;
import com.limemartini.domain.vo.BlogUserLoginVo;
import com.limemartini.domain.vo.UserInfoVo;
import com.limemartini.service.BlogLoginService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.JwtUtil;
import com.limemartini.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("bloglogin:"+userId, loginUser );
        // encapsulate
        UserInfoVo userInfoVo =  BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new BlogUserLoginVo(jwt, userInfoVo));
    }

    @Override
    public ResponseResult logout() {
        // get token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // get user id by parsing token
        Long userId = loginUser.getUser().getId();
        // delete user info saved in redis
        redisCache.deleteObject("bloglogin:"+userId.toString());
        return ResponseResult.okResult();
    }
}
