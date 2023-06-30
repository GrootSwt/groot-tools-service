package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;
import com.groot.business.dto.UserDTO;
import com.groot.business.model.User;
import com.groot.business.service.UserService;

import cn.dev33.satoken.annotation.SaCheckLogin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
@SaCheckLogin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Response<UserDTO> getUserInfo() {
        UserDTO user = userService.getUserById();
        return Response.success("获取当前登录用户信息成功", user);
    }

    @GetMapping("list")
    public Response<List<User>> selectAll() {
        List<User> users = userService.list();
        users.forEach(user -> user.setPassword(null));
        return Response.success("获取用户列表成功", users);
    }
}
