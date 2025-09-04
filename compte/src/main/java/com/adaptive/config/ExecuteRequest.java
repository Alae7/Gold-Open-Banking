package com.adaptive.config;

import com.adaptive.entity.NameApi;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ExecuteRequest {

    NameApi nameApi ;

    Map<String, String> pathParams;

    Object requestBody ;

    String banqueCode ;
}
