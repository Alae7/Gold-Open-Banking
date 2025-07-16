package com.adaptive.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "global.params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Component
@Setter
public class GlobalConfig {
    int p1;
    int p2;
}
