package com.vrv.vap.upms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wh1107066
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.vrv.vap.upms.mapper"})
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }
}
