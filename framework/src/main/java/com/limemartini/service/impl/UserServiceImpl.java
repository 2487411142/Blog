package com.limemartini.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.UserAdditionDto;
import com.limemartini.domain.dto.UserModificationDto;
import com.limemartini.domain.entity.Role;
import com.limemartini.domain.entity.User;
import com.limemartini.domain.entity.UserRole;
import com.limemartini.domain.vo.*;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.exception.SystemException;
import com.limemartini.mapper.UserMapper;
import com.limemartini.service.RoleService;
import com.limemartini.service.UserRoleService;
import com.limemartini.service.UserService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-06-13 19:55:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserVo userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        return BeanCopyUtils.copyBean(user, UserVo.class);
    }

    @Override
    public void updateUserInfo(User user) {
        updateById(user);
    }

    @Override
    public void register(User user) {
        // check not null and not empty
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        // check duplicate
        if (checkUserNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (checkNickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (checkEmailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // encode pwd
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        // save into db
        save(user);
    }

    @Override
    public ResponseResult listUsersInPages(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(userName)) {
            queryWrapper.like(User::getUserName, userName);
        }
        if (StringUtils.hasText(phonenumber)){
            queryWrapper.eq(User::getPhonenumber,phonenumber);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq(User::getStatus, status);
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<User> records = page.getRecords();
        List<UserInfoVo> userInfoVos = BeanCopyUtils.copyBeanList(records, UserInfoVo.class);

        return ResponseResult.okResult(new PageVo(userInfoVos, page.getTotal()));
    }

    @Override
    public ResponseResult addNewUser(UserAdditionDto dto) {
        User userToAdd = BeanCopyUtils.copyBean(dto, User.class);
        // set creator/updater info
        Long userId = SecurityUtils.getUserId();
        userToAdd.setCreateBy(userId);
        userToAdd.setUpdateBy(userId);
        // set create/update info
        Date now = new Date();
        userToAdd.setCreateTime(now);
        userToAdd.setUpdateTime(now);
        // encode pwd
        String encoded = passwordEncoder.encode(userToAdd.getPassword());
        userToAdd.setPassword(encoded);

        save(userToAdd);

        // save user-role relation
        List<UserRole> userRoles = dto.getRoleIds().stream()
                .map(RoleId -> new UserRole(userToAdd.getId(), RoleId))
                .toList();
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeUser(List<Long> ids) {
        for (Long id : ids) {
            if (SecurityUtils.getUserId().equals(id)) {
                return ResponseResult.errorResult(500, "cannot remove current user");
            }
            removeById(id);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult displayUserInfoAndRole(Long id) {
        //build field user
        User user = getById(id);
        BackgroundUserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, BackgroundUserInfoVo.class);

        //build field roles
        List<Role> roles = roleService.list();

        //build field roleIds
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .toList();
        return ResponseResult.okResult(new UserInfoAndRoleVo(roleIds, roles, userInfoVo));
    }

    @Override
    public ResponseResult updateUser(UserModificationDto dto) {
        User userToUpdate = BeanCopyUtils.copyBean(dto, User.class);
        updateById(userToUpdate);

        Long userId = userToUpdate.getId();
        // delete old user-role relation
        LambdaQueryWrapper<UserRole> deleteQueryWrapper = new LambdaQueryWrapper<>();
        deleteQueryWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(deleteQueryWrapper);

        // save new user-role relation
        List<UserRole> newRelation = dto.getRoleIds().stream()
                .map(roleId -> new UserRole(userId, roleId))
                .toList();
        userRoleService.saveBatch(newRelation);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeUserStatus(User user) {
        user.setUpdateBy(SecurityUtils.getUserId());
        user.setUpdateTime(new Date());
        updateById(user);
        return ResponseResult.okResult();
    }

    private boolean checkUserNameExist(String userName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }

    private boolean checkNickNameExist(String nickName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean checkEmailExist(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }
}
