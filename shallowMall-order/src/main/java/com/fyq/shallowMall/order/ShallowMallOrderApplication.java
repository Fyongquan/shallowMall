package com.fyq.shallowMall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyq.shallowMall.order.dao")
public class ShallowMallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallOrderApplication.class, args);
    }

}
