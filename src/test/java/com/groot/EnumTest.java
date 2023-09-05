package com.groot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.groot.business.bean.enums.ChatOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {

    private final ObjectMapper objectMapper;

    @Autowired
    public EnumTest(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    public void testEnumToStringMethod() throws Exception {
        String str = objectMapper.writeValueAsString(ChatOperationType.SEND);
        System.out.println(str);
    }
}
