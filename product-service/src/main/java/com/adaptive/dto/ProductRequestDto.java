package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductRequestDto {

    private Double  montantDemande;

    private Integer duree;

    private String codeBank;

    private String  compteType;

    private Double tauxInteret;

    private Double  remboursement;

}
