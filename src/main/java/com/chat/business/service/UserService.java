package com.chat.business.service;

import com.chat.business.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User getUser(User user);
}
