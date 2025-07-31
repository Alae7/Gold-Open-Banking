package com.adaptive.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EcheanceResponseDto {

    private int numero;

    private LocalDate dateEcheance;

    private double montant;

    private boolean paye;

    private Long rib;

}
