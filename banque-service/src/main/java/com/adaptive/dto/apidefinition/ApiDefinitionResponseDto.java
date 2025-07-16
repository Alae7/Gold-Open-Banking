package com.adaptive.dto.apidefinition;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApiDefinitionResponseDto {

    private String uuid;

    private String name;

    private String method;

    private String url;

    private String headersJson;

    private String queryParamsJson;

    private String bodyJson;

    private String banqueUuid;

    private String description;



}
