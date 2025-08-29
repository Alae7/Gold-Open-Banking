package com.adaptive;

import com.adaptive.config.CustomerConfig;
import com.adaptive.config.GlobalConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({GlobalConfig.class, CustomerConfig.class})
public class CustomerServiceApplication implements CommandLineRunner {


    private final GlobalConfig globalConfig;
    private final CustomerConfig customerConfig;

    public CustomerServiceApplication(GlobalConfig globalConfig , CustomerConfig customerConfig) {
        this.globalConfig = globalConfig;
        this.customerConfig = customerConfig;
    }


    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("✅ Global Params: " + globalConfig.getP1() + " and " + globalConfig.getP2());
        System.out.println("✅ Customer Params: " + customerConfig.getX() + " and " + customerConfig.getY());


    }
}
