package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ApiDefinitionServiceImpl implements ApiDefinitionService {


    @Override
    public ApiDefinitionResponseDto findByUuid(String uuid) {
        return null;
    }

    @Override
    public List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid) {
        return List.of();
    }

    @Override
    public ApiDefinitionResponseDto create(ApiDefinitionRequestDto apiDefinitionRequestDto) {
        return null;
    }

    @Override
    public void update(String uuid,ApiDefinitionRequestDto apiDefinitionRequestDto) {

    }

    @Override
    public void delete(String uuid) {

    }
}
