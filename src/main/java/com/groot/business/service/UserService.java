package com.groot.business.service;

import com.groot.business.dto.UserDTO;
import com.groot.business.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    UserDTO getUserByAccountAndPassword(String account, String password);

    UserDTO getUserById();

}
