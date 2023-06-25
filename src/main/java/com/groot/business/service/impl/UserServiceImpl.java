package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groot.base.bean.CurrentUser;
import com.groot.base.config.CurrentUserStore;
import com.groot.base.exception.BusinessRuntimeException;
import com.groot.business.mapper.UserMapper;
import com.groot.business.model.User;
import com.groot.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final CurrentUserStore currentUserStore;

    @Autowired
    public UserServiceImpl(final UserMapper userMapper, final CurrentUserStore currentUserStore) {
        this.userMapper = userMapper;
        this.currentUserStore = currentUserStore;
    }
    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", user.getAccount());
        wrapper.eq("password", user.getPassword());
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void checkLoginStatus() {
        CurrentUser currentUser = currentUserStore.getCurrentUser();
        if (currentUser == null || currentUser.getUserId() == null || "".equals(currentUser.getUserId())) {
            throw new BusinessRuntimeException("获取好友信息失败，请重新登陆", 401);
        }
        User user = userMapper.selectById(currentUser.getUserId());
        if (user == null) {
            throw new BusinessRuntimeException("获取好友信息失败，请重新登陆", 401);
        }
    }
}
