package com.adaptive.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NotificationRequestDto {

    private String transactionUuid;
    private String notificationType;

}
