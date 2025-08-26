package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ApiDefinitionService {

    ApiDefinitionResponseDto findByUuid(String uuid);

    List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid);

    ApiDefinitionResponseDto create(ApiDefinitionRequestDto apiDefinitionRequestDto);

    List<ApiDefinitionResponseDto> findByMethod(HttpMethod httpMethod);

    void update(String uuid,ApiDefinitionRequestDto apiDefinitionRequestDto);

    void delete(String uuid);

    public ResponseEntity<?> executeApi(String uuid , Map<String, String> pathParams,  Map<String, String> queryParams ,Map<String, String> requestBody);


}
