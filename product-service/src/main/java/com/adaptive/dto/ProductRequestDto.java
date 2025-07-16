package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductRequestDto {

    private Double  price;

    private int times;

    private String  compteType;

    private int  taux;

}
