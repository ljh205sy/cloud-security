package com.vrv.vap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author wh1107066
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MssTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MssTestApplication.class, args);
    }
}
