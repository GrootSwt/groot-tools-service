package com.groot.business.controller;

import com.groot.business.bean.request.RegisterRequest;
import com.groot.business.bean.response.base.Response;
import com.groot.business.service.RegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public Response<Void> register(@Validated @RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return Response.success("已接收到注册申请，请耐心等待审批");
    }
}
