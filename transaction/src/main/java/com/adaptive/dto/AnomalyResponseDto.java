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
public class AnomalyResponseDto {


    private Long id;

    private Double amount;

    private TypeTransaction type;

    private Long targetRib;

    private Long sourceRib;

    private LocalDateTime createDateTime;

    private String tele;

    private boolean traite;


}
