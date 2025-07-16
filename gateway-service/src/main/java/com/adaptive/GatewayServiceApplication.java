package com.adaptive;

import com.adaptive.config.GatewayConfig;
import com.adaptive.config.GlobalConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({GlobalConfig.class, GatewayConfig.class})
public class GatewayServiceApplication implements CommandLineRunner {

    private final GlobalConfig globalConfig;
    private final GatewayConfig gatewayConfig;

    public GatewayServiceApplication(GlobalConfig globalConfig , GatewayConfig gatewayConfig) {
        this.globalConfig = globalConfig;
        this.gatewayConfig = gatewayConfig;
    }


    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public DiscoveryClientRouteDefinitionLocator locator(
            ReactiveDiscoveryClient discoveryClient,
            DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("✅ Global Params: " + globalConfig.getP1() + " and " + globalConfig.getP2());
        System.out.println("✅ Gateway Params: " + gatewayConfig.getXx() + " and " + gatewayConfig.getYy());

    }
}
