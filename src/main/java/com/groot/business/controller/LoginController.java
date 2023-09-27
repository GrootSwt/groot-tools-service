package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;

import com.groot.business.bean.request.LoginRequest;
import com.groot.business.bean.response.UserResponse;
import com.groot.business.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public Response<UserResponse> login(@Validated @RequestBody LoginRequest request) {
        UserResponse userResponse = userService.getUserByAccountAndPassword(request.getAccount(), request.getPassword());
        StpUtil.login(userResponse.getId(),
                SaLoginConfig
                        .setExtra("account", userResponse.getAccount())
                        .setExtra("displayName", userResponse.getDisplayName()));

        return Response.success("登陆成功", userResponse);
    }
}
