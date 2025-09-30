package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductResponseDto {

    private Double  montantDemande;

    private Integer duree;

    private String  compteType;

    private Double tauxInteret;

    private String description;

    private Double  remboursement;

    private String uuid;

    private String codeBank;


}
