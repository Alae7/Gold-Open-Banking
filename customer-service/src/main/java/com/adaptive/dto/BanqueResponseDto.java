package com.adaptive.dto;



import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BanqueResponseDto {

    private String uuid;

    private String code;

    private String name;

    private String adresse;

    private List<ApiDefinitionResponseDto> apiDefinitions;



}
