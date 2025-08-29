package com.adaptive.dto;


import com.adaptive.entity.TypeCompte;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CompteRequestDto {

    private String  banqueUuid;
    private TypeCompte typeCompte;
    private String  customerUuid;

}
