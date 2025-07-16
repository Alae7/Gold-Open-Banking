package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TransactionRequestDto {

    private Double amount;

    private String type;

    private Long targetRib;

    private Long sourceRib;

}
