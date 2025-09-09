package com.adaptive.model;


import com.adaptive.entity.NameApi;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApiDefinitionResponseDto {

    private String uuid;

    private NameApi name;

    private String method;

    private String url;

    private String banqueUuid;




}
