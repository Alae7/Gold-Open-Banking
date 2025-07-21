package com.adaptive.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CompteRequestDto {

    private String  banqueUuid;
    private String  typeCompte;
    private String  customerUuid;

}
