package com.groot.business.controller;

import com.groot.base.bean.result.BaseResponse;
import com.groot.base.exception.BusinessRuntimeException;
import com.groot.business.utils.JWTUtil;
import com.groot.business.model.User;
import com.groot.business.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {


    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public BaseResponse login(@Validated @RequestBody User user, HttpServletResponse response) {
        User result = userService.getUser(user);
        if (null == result) {
            throw new BusinessRuntimeException("账号或密码不正确");
        }

        Cookie tokenCookie = new Cookie("token", JWTUtil.getToken(result));
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);

        Cookie userIdCookie = new Cookie("userId", result.getId());
        userIdCookie.setPath("/");
        response.addCookie(userIdCookie);

        return BaseResponse.success("登陆成功");
    }
}
