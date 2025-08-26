package com.adaptive.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "ApiDefinition")
public class ApiDefinition extends BaseModel{

    @Field
    private String name;

    @Field
    private HttpMethod method;

    @Field
    private String url;

    @Field
    private List<Headers> header;

    @Field
    private List<Parameter> params;

    @Field
    private String bodyJson;

    @Field
    private String banqueUuid;





}
