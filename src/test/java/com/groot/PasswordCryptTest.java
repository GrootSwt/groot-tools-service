package com.groot;

import cn.dev33.satoken.secure.BCrypt;

public class PasswordCryptTest {

    public static void main(String[] args) {
        String password = "123456";
        System.out.println(BCrypt.hashpw(password));
    }
}
