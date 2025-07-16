package com.adaptive.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "customer.params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Component
@Setter
public class CustomerConfig {
    int x;
    int y;
}
