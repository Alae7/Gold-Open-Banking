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

    @Column
    private Double  solde;
    @Column
    private String  customerUuid;

    @Column
    private String  typeCompte;

    @Column(nullable = false, unique = true)
    private Long numCompte;

    @Column(nullable = false, unique = true)
    private String  banqueUuid;



    @PrePersist
    public void prePersist() {
        this.setDeleted(false);
        this.setStatut("PENDING");
        this.solde = 0.00;
    }

}
