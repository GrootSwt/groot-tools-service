package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.dto.UserDTO;
import com.groot.business.mapper.UserMapper;
import com.groot.business.model.User;
import com.groot.business.service.UserService;

import cn.dev33.satoken.stp.StpUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public UserDTO getUserByAccountAndPassword(String account, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        wrapper.eq("password", password);
        User user = userMapper.selectOne(wrapper);
        if (null == user) {
            throw new BusinessRuntimeException("账号或密码不正确");
        }
        UserDTO result = new UserDTO(user.getId(), user.getAccount(), user.getDisplayName(), user.getPhoneNumber());
        return result;
    }

    @Override
    public UserDTO getUserById() {
        String userId = StpUtil.getLoginIdAsString();
        User user = userMapper.selectById(userId);
        UserDTO result = new UserDTO(userId, user.getAccount(), user.getDisplayName(), user.getPhoneNumber());
        return result;
    }
}
