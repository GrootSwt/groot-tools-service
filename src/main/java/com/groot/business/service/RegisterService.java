package com.groot.business.service;

import com.groot.business.bean.request.RegisterRequest;
import com.groot.business.model.Register;

public interface RegisterService {
    void register(RegisterRequest registerRequest);

    void approve(String id);

    Register getRegisterById(String id);
}
