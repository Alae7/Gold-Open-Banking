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
@Document(collection = "Address")
public class Address extends BaseModel{

    @Field
    private String street;

    @Field
    private String city;

    @Field
    private String houseNumber;

    @Field
    private String postalCode;

}
