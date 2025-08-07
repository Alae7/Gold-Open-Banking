package com.adaptive.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "banque")
public class Banque extends BaseModel {

    @Field
    private String name;

    @Field
    private String adresse;

    @DBRef
    private List<ApiDefinition> apiDefinitions;


}
