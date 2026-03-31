package com.lifeplan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lifeplan.mapper")
public class LifePlanApplication {
    public static void main(String[] args) {
        SpringApplication.run(LifePlanApplication.class, args);
    }
}
