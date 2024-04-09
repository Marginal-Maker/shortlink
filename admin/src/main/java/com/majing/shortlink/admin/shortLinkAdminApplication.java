package com.majing.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author majing
 * @date 2024-04-08 17:00
 * @Description admin启动类
 */
@SpringBootApplication
@MapperScan("com.majing.shortlink.admin.dao.mapper")
public class shortLinkAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(shortLinkAdminApplication.class, args);
    }
}
