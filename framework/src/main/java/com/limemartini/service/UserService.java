package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.UserAdditionDto;
import com.limemartini.domain.dto.UserModificationDto;
import com.limemartini.domain.entity.User;
import com.limemartini.domain.vo.UserVo;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-06-13 19:55:41
 */
public interface UserService extends IService<User> {

    UserVo userInfo();

    void updateUserInfo(User user);

    void register(User user);

    ResponseResult listUsersInPages(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addNewUser(UserAdditionDto dto);

    ResponseResult removeUser(List<Long> id);

    ResponseResult displayUserInfoAndRole(Long id);

    ResponseResult updateUser(UserModificationDto dto);

    ResponseResult changeUserStatus(User user);
}

