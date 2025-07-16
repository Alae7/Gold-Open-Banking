package com.adaptive.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private String method;

    @Field
    private String url;

    @Field
    private String headersJson;

    @Field
    private String queryParamsJson;

    @Field
    private String bodyJson;

    @Field
    private String banqueUuid;


}
