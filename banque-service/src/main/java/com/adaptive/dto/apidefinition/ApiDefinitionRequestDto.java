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

public class ApiDefinitionRequestDto {


    private String name;

    private HttpMethod method;

    private String url;

    /**
     * Liste d'en-têtes (ex: [{"key":"Content-Type","value":"application/json"}, ...])
     */
    private List<Headers> headers;

    /**
     * Liste de paramètres (query/path)
     */
    private List<Parameter> queryParams;

    private String bodyJson;

    private String banqueUuid;

    private String description;

}
