package com.fyq.shallowMall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

//@SpringBootApplication(scanBasePackages = {
//        "com.fyq.shallowMall.product",
//        "com.fyq.common"
//})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyq.shallowMall.product.dao")
public class ShallowMallProductApplication {

    /**
     * 1、整合MyBatis-Plus
     *  1）、导入依赖
     * <dependency>
     *     <groupId>com.baomidou</groupId>
     *     <artifactId>mybatis-plus-boot-starter</artifactId>
     *     <version>3.2.0</version>
     *     <scope>compile</scope>
     * </dependency>
     *  2）、配置
     *      1、配置数据源
     *          1）、导入数据库的驱动
     *          2）、在application.yml中配置数据源信息
     *      2、配置MyBatis-Plus
     *          1）、使用@MapperScan
     *          2）、告诉MyBatis-Plus，sql映射文件位置
     */
    public static void main(String[] args) {
        SpringApplication.run(ShallowMallProductApplication.class, args);
    }

}
