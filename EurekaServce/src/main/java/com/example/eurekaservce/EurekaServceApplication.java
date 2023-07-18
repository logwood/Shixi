package com.example.eurekaservce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServceApplication.class, args);
    }

}
