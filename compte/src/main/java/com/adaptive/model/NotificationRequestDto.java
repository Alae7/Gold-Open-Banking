package com.adaptive.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NotificationRequestDto {

    private Long compteRib;
    private String notificationType;

}
