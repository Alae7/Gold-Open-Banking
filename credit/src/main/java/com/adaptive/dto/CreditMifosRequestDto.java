package com.adaptive.dto;


import com.adaptive.entity.CreditStatus;
import com.adaptive.entity.Echeance;
import com.adaptive.model.TypeCompte;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditMifosRequestDto {


    private String uuid;

    private Double montantDemande;

    private Double tauxInteret;

    private Double remboursement;

    private Integer duree;

    private String typeCredit;

    private LocalDate dateDebut;

    private CreditStatus status;

    private LocalDate dateFin;

    private Long compteRib;

    private TypeCompte typeCompte;

    private List<Echeance> echeances;


}
