package com.adaptive.model;


import com.adaptive.entity.TypeCompte;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AccountRequestDto {

    private TypeCompte typeAccount;
    private String  clientUuid;

}
