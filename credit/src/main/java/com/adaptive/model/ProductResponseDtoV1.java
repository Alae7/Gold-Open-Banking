package com.adaptive.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductResponseDtoV1 {

    private Double  montantDemande;

    private Integer duree;

    private String  compteType;

    private Double tauxInteret;

    private Double  remboursement;

    private int frequency;

    private String uuid;

    private String codeBank;


}
