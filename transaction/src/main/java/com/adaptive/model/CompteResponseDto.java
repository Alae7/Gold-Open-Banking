package com.adaptive.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CompteResponseDto {

    private String  customerUuid;
    private Double  solde;
    private Long numCompte;
    private Long rib;


}
