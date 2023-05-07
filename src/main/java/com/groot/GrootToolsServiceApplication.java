package com.groot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.groot.business.mapper")
public class GrootToolsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrootToolsServiceApplication.class, args);
    }

}
