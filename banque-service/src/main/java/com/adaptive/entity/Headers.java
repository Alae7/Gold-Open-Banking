package com.adaptive.entity;

import lombok.*;

import org.springframework.data.mongodb.core.mapping.Field;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Headers  {

    @Field("key")
    private String key;

    @Field("value")
    private String value;

}
