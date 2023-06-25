package com.groot.business.service;

import com.groot.business.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User getUser(User user);

    void  checkLoginStatus();
}
