package com.fyq.shallowMall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyq.shallowMall.ware.dao")
public class ShallowMallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallWareApplication.class, args);
    }

}
