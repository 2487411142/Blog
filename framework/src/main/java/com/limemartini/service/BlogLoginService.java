package com.limemartini.service;

import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
