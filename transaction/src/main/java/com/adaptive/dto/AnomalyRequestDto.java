package com.adaptive.dto;


import com.adaptive.entity.TypeTransaction;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AnomalyRequestDto {

    private String transactionUuid;

    private String tele;

}
