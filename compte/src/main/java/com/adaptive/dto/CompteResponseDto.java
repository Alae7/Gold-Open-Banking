package com.adaptive.dto;


import com.adaptive.entity.StatusCompte;
import com.adaptive.entity.TypeCompte;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CompteResponseDto {

    private String  customerUuid;
    private Double  solde;
    private Long rib;
    private TypeCompte typeCompte;
    private StatusCompte Statut;
    private LocalDateTime createDateTime;


}
