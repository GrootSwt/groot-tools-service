package com.groot.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.groot.business.bean.enums.RegisterStatus;
import com.groot.business.bean.request.RegisterRequest;
import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.mapper.RegisterMapper;
import com.groot.business.mapper.UserMapper;
import com.groot.business.model.Register;
import com.groot.business.model.User;
import com.groot.business.service.RegisterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Value("{application.website.name}")
    private String websiteName;
    @Value("{application.website.location}")
    private String websiteLocation;

    private final RegisterMapper registerMapper;
    private final UserMapper userMapper;


    public RegisterServiceImpl(final RegisterMapper registerMapper, final UserMapper userMapper) {
        this.registerMapper = registerMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest registerRequest) {
        Register register = new Register();
        register.setAccount(registerRequest.getAccount());
        register.setPassword(registerRequest.getPassword());
        register.setDisplayName(registerRequest.getDisplayName());
        register.setPhoneNumber(registerRequest.getPhoneNumber());
        register.setEmail(registerRequest.getEmail());
        register.setStatus(RegisterStatus.UNAPPROVED);
        registerMapper.insert(register);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(String id) {
        Register register = registerMapper.selectById(id);
        if (null == register) {
            throw new BusinessRuntimeException("注册数据不存在", 404);
        }
        UpdateWrapper<Register> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", RegisterStatus.APPROVED);
        registerMapper.update(register, updateWrapper);

        User newUser = new User();
        newUser.setAccount(register.getAccount());
        newUser.setPassword(register.getPassword());
        newUser.setPhoneNumber(register.getPhoneNumber());
        newUser.setDisplayName(register.getDisplayName());
        newUser.setEmail(register.getEmail());
        userMapper.insert(newUser);
    }

    @Override
    public Register getRegisterById(String id) {
        return registerMapper.selectById(id);
    }
}
