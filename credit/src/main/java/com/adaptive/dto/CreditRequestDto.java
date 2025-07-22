package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditRequestDto {

    private String productUuid;

    private String customerUuid;

    private Double remboursement;


}
