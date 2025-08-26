package com.adaptive.entity;

import lombok.*;

import org.springframework.data.mongodb.core.mapping.Field;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Parameter{

    @Field("name")
    private String name;

}
