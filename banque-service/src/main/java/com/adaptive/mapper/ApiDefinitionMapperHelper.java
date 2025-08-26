package com.adaptive.mapper;


import org.springframework.http.HttpMethod;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;



@Component
public class ApiDefinitionMapperHelper {


    @Named("toString")
    public String toString(HttpMethod method) {
        return method.toString();
    }


}
