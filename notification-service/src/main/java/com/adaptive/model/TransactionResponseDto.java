package com.adaptive.model;


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

    private String type;

    private Long targetRib;

    private Long sourceRib;

    private String status;

    private LocalDateTime createDateTime;


}
