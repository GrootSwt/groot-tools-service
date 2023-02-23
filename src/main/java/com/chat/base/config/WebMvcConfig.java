package com.chat.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    private final SearchDataArgumentResolver searchDataArgumentResolver;

    private final LoginInterceptor loginInterceptor;

    @Autowired
    public WebMvcConfig(SearchDataArgumentResolver searchDataArgumentResolver, LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.searchDataArgumentResolver = searchDataArgumentResolver;
    }

    /**
     * handler method searchData 形参解析器
     * @param argumentResolvers handler method 形参解析器列表
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // searchData形参解析器
        argumentResolvers.add(searchDataArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/login");
        super.addInterceptors(registry);
    }
}
