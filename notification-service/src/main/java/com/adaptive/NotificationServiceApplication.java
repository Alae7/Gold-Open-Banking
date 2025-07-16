package com.adaptive;

import com.adaptive.config.GlobalConfig;
import com.adaptive.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties({GlobalConfig.class, NotificationConfig.class})
@EnableFeignClients
public class NotificationServiceApplication implements CommandLineRunner {

    private final GlobalConfig globalConfig;
    private final NotificationConfig notificationConfig;

    public NotificationServiceApplication(GlobalConfig globalConfig , NotificationConfig notificationConfig) {
        this.globalConfig = globalConfig;
        this.notificationConfig = notificationConfig;
    }


    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("✅ Global Params: " + globalConfig.getP1() + " and " + globalConfig.getP2());
        System.out.println("✅ Notification Params: " + notificationConfig.getX() + " and " + notificationConfig.getY());


    }
}
