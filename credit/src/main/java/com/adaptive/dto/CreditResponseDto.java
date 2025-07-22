package com.adaptive.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditResponseDto {

    private String  customerUuid;
    private Double  solde;
    private Long numCompte;
    private Long rib;
    private String  typeCompte;
    private String Statut;
    private LocalDateTime createDateTime;


}
