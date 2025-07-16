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
    private Double  price;

    private int times;

    private String  compteType;

    private int  taux;

    private Double  remboursement;

}
