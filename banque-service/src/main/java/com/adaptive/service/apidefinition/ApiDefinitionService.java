package com.adaptive.service.apidefinition;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ApiDefinitionService {


    ApiDefinitionResponseDto findByUuid(String uuid);

    List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid);

    String create(List<ApiDefinitionRequestDto> apiDefinitionRequestDto);

    void update(String uuid,String url);

    void delete(String uuid);

    public ResponseEntity<?> executeApi(ExecuteRequest executeRequest);

    public List<Object> getFromAllApi();

    public List<Object> getFromAllApi(Map<String, String> pathParams);
}
