package com.fyq.shallowMall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients(basePackages = "com.fyq.shallowMall.ware.feign")
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyq.shallowMall.ware.dao")
public class ShallowMallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallWareApplication.class, args);
    }

}
