package com.groot.business.service.impl;

import com.groot.business.bean.RegisterStatusEnum;
import com.groot.business.bean.request.RegisterRequest;
import com.groot.business.mapper.RegisterMapper;
import com.groot.business.model.Register;
import com.groot.business.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterMapper registerMapper;

    public RegisterServiceImpl(final RegisterMapper registerMapper) {
        this.registerMapper = registerMapper;
    }
    @Override
    public void register(RegisterRequest registerRequest) {
        Register register = new Register();
        register.setAccount(register.getAccount());
        register.setPassword(register.getPassword());
        register.setDisplayName(register.getDisplayName());
        register.setPhoneNumber(register.getPhoneNumber());
        register.setEmail(register.getEmail());
        register.setStatus(RegisterStatusEnum.UNAPPROVED);
        registerMapper.insert(register);
    }
}
