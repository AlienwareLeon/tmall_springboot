package com.how2java.tmall;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.how2java.tmall.util.PortUtil;
@SpringBootApplication
@EnableCaching
public class Application {
    static {
        PortUtil.checkPort(6379,"Redis 服务端",true);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/**
 * 增加注解： @EnableCaching 用于启动缓存
 2. 检查端口6379是否启动。 6379 就是 Redis 服务器使用的端口。
 如果未启动，那么就会退出 springboot。
 */
