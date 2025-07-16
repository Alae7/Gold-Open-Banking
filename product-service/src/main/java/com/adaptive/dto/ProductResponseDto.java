package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductResponseDto {

    private Double  price;

    private int times;

    private String  compteType;

    private int  taux;

    private Double  remboursement;

    private String uuid;


}
