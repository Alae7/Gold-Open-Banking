package com.adaptive.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "notification.params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Component
@Setter
public class NotificationConfig {
    int x;
    int y;
}
