package com.adaptive.dto.apidefinition;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class ApiDefinitionRequestDto {


    private String name;

    private String method;

    private String url;

    private String headersJson;

    private String queryParamsJson;

    private String bodyJson;

    private String banqueUuid;

    private String description;

}
