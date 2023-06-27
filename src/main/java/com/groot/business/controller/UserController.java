package com.groot.business.controller;

import com.groot.base.bean.result.ListResponse;
import com.groot.business.model.User;
import com.groot.business.service.UserService;

import cn.dev33.satoken.annotation.SaCheckLogin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@SaCheckLogin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ListResponse<User> selectAll() {
        List<User> users = userService.list();
        users.forEach(user -> user.setPassword(null));
        return ListResponse.success("获取用户列表成功", users);
    }
}
