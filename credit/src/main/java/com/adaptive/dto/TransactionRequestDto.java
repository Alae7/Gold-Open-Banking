package com.adaptive.dto;


import com.adaptive.entity.TypeTransaction;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TransactionRequestDto {

    private Double amount;

    private TypeTransaction type;

    private Long targetRib;

    private Long sourceRib;

}
