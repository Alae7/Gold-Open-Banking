package com.adaptive.utils;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.entity.ApiDefinition;

public class Utils {


    public static ApiDefinition update(ApiDefinitionRequestDto apiDefinitionRequestDto , ApiDefinition  apiDefinition) {

        if (apiDefinitionRequestDto != null) {
            if (apiDefinition != null) {

                if (apiDefinitionRequestDto.getHeaders() != null) {
                    apiDefinition.setHeader(apiDefinitionRequestDto.getHeaders());
                }
                if (apiDefinitionRequestDto.getQueryParams() != null) {
                    apiDefinition.setParams(apiDefinitionRequestDto.getQueryParams());
                }
                if (apiDefinitionRequestDto.getUrl() != null) {
                    apiDefinition.setUrl(apiDefinitionRequestDto.getUrl());
                }
            }

        }

        return apiDefinition;
    }

}
