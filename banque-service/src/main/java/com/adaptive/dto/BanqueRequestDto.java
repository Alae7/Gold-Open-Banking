package com.adaptive.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BanqueRequestDto {

    private String name;

    private String description;

    private String adresse;

}
