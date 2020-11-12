package com.ssm.lv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ssm.lv.mapper")
public class SpringbootLearnNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLearnNetworkApplication.class, args);
    }

}
