package com.groot.business.controller;

import com.groot.business.bean.request.RegisterRequest;
import com.groot.business.bean.response.base.Response;
import com.groot.business.model.Register;
import com.groot.business.service.RegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 审批通过
     *
     * @param id 注册id
     * @return 审批通过
     */
    @PutMapping("{id}/approve")
    public Response<Void> approve(@PathVariable String id) {
        registerService.approve(id);
        return Response.success("已审批通过");
    }

    @GetMapping("{id}")
    public Response<Register> getRegisterById(@PathVariable String id) {
        Register register = registerService.getRegisterById(id);
        return Response.success("获取成功", register);
    }

}
