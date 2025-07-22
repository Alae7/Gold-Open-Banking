package com.adaptive.dto;

import lombok.*;

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
