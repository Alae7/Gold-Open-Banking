package com.adaptive.entity;



import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed(unique = true)
    @Field
    private String cin;

    @Field
    private LocalDate birthDate;

    @Indexed(unique = true)
    @Field
    private String  email;

    @Field
    private String  password;

    @Field
    private String  phone;

    @Field
    private String  address;

    @Field
    private String  city;

    @Field
    private String  banqueUuid;

}
