package com.adaptive.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GdiRequestDto {

    private String  cin;

    private String  lastNam;

    private String  firstName;

    private int  nbrFemme;

    private int  nbrEnfant;

    private double income;

    private Long numInscription;

}
