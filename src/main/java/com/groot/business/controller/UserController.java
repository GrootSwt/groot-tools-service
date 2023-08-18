package com.groot.business.controller;

import com.groot.business.bean.response.AccountExistCheckResponse;
import com.groot.business.bean.response.base.Response;
import com.groot.business.bean.response.UserResponse;
import com.groot.business.model.User;
import com.groot.business.service.UserService;

import cn.dev33.satoken.annotation.SaCheckLogin;

import org.springframework.web.bind.annotation.*;

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
    public Response<UserResponse> getUserInfo() {
        UserResponse user = userService.getUserById();
        return Response.success("获取当前登录用户信息成功", user);
    }

    @GetMapping("list")
    public Response<List<User>> selectAll() {
        List<User> users = userService.list();
        users.forEach(user -> user.setPassword(null));
        return Response.success("获取用户列表成功", users);
    }

    @GetMapping("accountExistCheck")
    public Response<AccountExistCheckResponse> accountExistCheck(@RequestParam String account) {
        Boolean accountExist = userService.accountExistCheck(account);
        return Response.success("账号是否存在检测成功", AccountExistCheckResponse.builder().accountExist(accountExist).build());
    }
}
