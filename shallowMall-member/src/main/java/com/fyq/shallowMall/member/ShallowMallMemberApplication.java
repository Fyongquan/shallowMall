package com.fyq.shallowMall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.fyq.shallowMall.member.dao")
public class ShallowMallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallMemberApplication.class, args);
    }

}
