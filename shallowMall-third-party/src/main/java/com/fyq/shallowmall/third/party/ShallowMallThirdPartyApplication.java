package com.fyq.shallowmall.third.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ShallowMallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShallowMallThirdPartyApplication.class, args);
    }

}
