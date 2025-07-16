package com.adaptive.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "banque.params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Component
@Setter
public class BanqueConfig {
    int x;
    int y;
}
