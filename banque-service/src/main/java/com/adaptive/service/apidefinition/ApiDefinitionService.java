package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.entity.ApiDefinition;

import java.util.List;

public interface ApiDefinitionService {

    ApiDefinitionResponseDto findByUuid(String uuid);

    List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid);

    ApiDefinitionResponseDto create(ApiDefinitionRequestDto apiDefinitionRequestDto);

    void update(String uuid,ApiDefinitionRequestDto apiDefinitionRequestDto);

    void delete(String uuid);


}
