package com.groot.business.service;

import com.groot.business.bean.response.UserResponse;
import com.groot.business.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    UserResponse getUserByAccountAndPassword(String account, String password);

    UserResponse getUserById();

    Boolean accountExistCheck(String account);
}
