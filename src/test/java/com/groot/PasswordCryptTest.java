package com.groot;

import cn.dev33.satoken.secure.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordCryptTest {

    @Test
    public void passwordCryptTest() {
        String password = "123456";
        System.out.println(BCrypt.hashpw(password));
    }
}
