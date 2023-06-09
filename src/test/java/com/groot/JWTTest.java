package com.groot;

import com.groot.business.model.User;
import com.groot.business.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTest {

    @Test
    public void testVerify() {
        User user = new User();
        user.setId("1");
        user.setAccount("test1");
        String token = JWTUtil.getToken(user);
        System.out.println(token);
    }

}
