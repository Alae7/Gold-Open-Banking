package com.adaptive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BanqueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueServiceApplication.class, args);
    }

}
