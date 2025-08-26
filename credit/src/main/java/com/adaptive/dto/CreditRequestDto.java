package com.adaptive.dto;


import com.adaptive.model.TypeCompte;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditRequestDto {

    private String productUuid;

    private Long compteRib;

    private String typeCredit;

    private String home;

    private String hasCredit;

    private int anciennete;

    private String  cin;

    private int  age;

    private TypeCompte typeCompte;

    private String  lastNam;

    private String  firstName;

    private int  nbrFemme;

    private int  nbrEnfant;

    private double salaire;

    private Long numInscription;



}
