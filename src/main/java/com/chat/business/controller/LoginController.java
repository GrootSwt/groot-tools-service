package com.chat.business.controller;

import com.chat.base.bean.result.BaseResult;
import com.chat.base.exception.BusinessRuntimeException;
import com.chat.business.utils.JWTUtil;
import com.chat.business.model.User;
import com.chat.business.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public BaseResult login(@Validated @RequestBody User user, HttpServletResponse response) {
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

        return BaseResult.success("登陆成功");
    }
}
