package com.chat;

import com.chat.business.mapper.UserMapper;
import com.chat.business.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTest {

    private final UserMapper userMapper;

    @Autowired
    public UserTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
