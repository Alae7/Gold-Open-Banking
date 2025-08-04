package com.adaptive.entity;

import jakarta.persistence.*;
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

    private Long rib;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_uuid", foreignKey = @ForeignKey(name = "echeance_foreignKey"))
    private Credit credit;

}
