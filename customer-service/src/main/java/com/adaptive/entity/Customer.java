package com.adaptive.entity;



import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "customers")
public class Customer extends BaseModel {

    @Field
    private String  firstName;

    @Field
    private String  lastName;

    @Field
    private String cin;

    @Field
    private LocalDate birthDate;

    @Field
    private String  email;

    @Field
    private String  password;

    @Field
    private String  phone;

    @DBRef
    private Image image;

    @Field
    private String  address;

    @Field
    private String  city;

}
