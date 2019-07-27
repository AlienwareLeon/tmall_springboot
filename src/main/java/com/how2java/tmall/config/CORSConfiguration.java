package com.how2java.tmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
/*
    配置类，用于允许所有的请求都跨域
    二次请求，第一次是获取html页面，第二次通过html页面上的JS代码异步获取数据
    部署到服务器面临跨域请求问题，所以允许所有访问都跨域
    不会出现ajax获取，数据获取不到的问题
 */