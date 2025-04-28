package com.fyq.shallowMall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyq.shallowMall.coupon.dao")
public class ShallowMallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallCouponApplication.class, args);
    }

}
