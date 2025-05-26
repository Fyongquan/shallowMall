package com.fyq.shallowmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShallowMallSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallSearchApplication.class, args);
    }

}
