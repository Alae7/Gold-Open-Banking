package com.adaptive.dto.apidefinition;


import com.adaptive.entity.Headers;
import com.adaptive.entity.Parameter;
import lombok.*;
import org.springframework.http.HttpMethod;

import java.util.List;

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

    /**
     * Liste d'en-têtes (ex: [{"key":"Content-Type","value":"application/json"}, ...])
     */
    private List<Headers> header;

    /**
     * Liste de paramètres (query/path)
     */
    private List<Parameter> params;

    private String bodyJson;

    private String banqueUuid;

    private String description;



}
