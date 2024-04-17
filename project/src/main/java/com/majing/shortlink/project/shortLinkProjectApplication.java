package com.majing.shortlink.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author majing
 * @date 2024-04-08 17:00
 * @Description project启动类
 */
@SpringBootApplication
@MapperScan("com.majing.shortlink.project.dao.mapper")
public class shortLinkProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(shortLinkProjectApplication.class, args);
    }
}
