package com.adaptive.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditResponseDto {

    private Long  compteRib;
    private Double  solde;
    private Long rib;
    private String typeCredit;
    private String  typeCompte;
    private String Statut;
    private LocalDateTime createDateTime;
    private List<EcheanceResponseDto> echeanceResponseDtos;


}
