package com.boyouquan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.boyouquan.dao"})
public class BoYouQuanWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoYouQuanWebApplication.class, args);
    }

}
