package com.adaptive.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Notification_CreditRequestDto {

    private Long  compteRib;
    private String typeCredit;
    private Double montantDemande;
    private String notificationType;
    private List<EcheanceResponseDto> echeanceResponseDtos;

}
