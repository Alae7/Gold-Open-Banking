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
public class Credit extends BaseModel {

    private Double montantDemande;

    private Double tauxInteret;

    private Double remboursement;

    private Integer duree;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Long compteRib;

}
