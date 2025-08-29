package com.adaptive.dto.apidefinition;

import com.adaptive.entity.NameApi;
import lombok.*;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class ApiDefinitionRequestDto {


    private NameApi name;

    private HttpMethod method;

    private String url;

    private String banqueUuid;


}
