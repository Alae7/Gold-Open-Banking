package com.adaptive.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Echeance extends BaseModel{

    private int numero;

    private LocalDate dateEcheance;

    private double montant;

    private boolean paye = false;

    private String creditUuid;

}
