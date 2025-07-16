package com.adaptive.entity;


import com.adaptive.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Compte extends BaseModel {


    private Double  solde;

    private String  customerUuid;

    @Column(nullable = false, unique = true)
    private Long numCompte;

    private String  banqueUuid;


    @PrePersist
    public void prePersist() {
        this.setDeleted(false);
        this.setStatut(true);
        this.solde = 0.00;
        this.numCompte = Utils.generatorNumerousCompte();
    }

}
