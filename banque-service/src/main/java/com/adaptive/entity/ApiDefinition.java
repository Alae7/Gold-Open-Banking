package com.adaptive.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpMethod;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "ApiDefinition")
public class ApiDefinition extends BaseModel{

    @Field
    private NameApi name;

    @Field
    private HttpMethod method;

    @Field
    private String url;

    @Field
    private String banqueUuid;





}
