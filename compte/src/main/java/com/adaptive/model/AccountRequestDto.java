package com.adaptive.model;


import com.adaptive.entity.TypeCompte;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AccountRequestDto {

    private TypeCompte typeAccount;
    private String  clientUuid;
    private LocalDateTime createDateTime;

}
