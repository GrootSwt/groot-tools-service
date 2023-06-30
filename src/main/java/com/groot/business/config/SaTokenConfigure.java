package com.groot.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;

@Configuration
public class SaTokenConfigure {

  @Bean
  StpLogic getStpLogicJwt() {
    return new StpLogicJwtForSimple();
  }
}