package com.adaptive.dto;


import com.adaptive.entity.TypeTransaction;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TransactionResponseDto {


    private String uuid;

    private Double amount;

    private TypeTransaction type;

    private Long targetRib;

    private Long sourceRib;

    private String status;

    private LocalDateTime createDateTime;


}
