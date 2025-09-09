package com.adaptive.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product extends BaseModel {

    @Column(nullable = false)
    private Double  montantDemande;

    private Integer duree;

    private String codeBank;

    private String  compteType;

    private Double tauxInteret;

    private Double  remboursement;

    private int frequency;

}
