package com.adaptive.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Notification_CompteRequestDto {

    private Long compteRib;
    private String notificationType;

}
