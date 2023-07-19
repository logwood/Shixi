package org.example.shixi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShixiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShixiApplication.class, args);
    }

}
