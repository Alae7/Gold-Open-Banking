package com.adaptive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteApplication.class, args);
    }

}
