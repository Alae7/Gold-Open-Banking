package com.adaptive.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "credit" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Echeance> echeances;
}
