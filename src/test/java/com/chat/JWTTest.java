package com.chat;

import com.chat.business.model.User;
import com.chat.business.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTest {

    @Test
    public void testVerify() {
        User user = new User();
        user.setId("1");
        user.setUsername("test1");
        String token = JWTUtil.getToken(user);
        System.out.println(token);
    }

}
