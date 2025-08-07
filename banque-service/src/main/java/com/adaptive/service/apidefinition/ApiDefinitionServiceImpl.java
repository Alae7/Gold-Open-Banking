package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.entity.ApiDefinition;
import com.adaptive.mapper.ApiDefinitionMapper;
import com.adaptive.repository.ApiDefinitionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ApiDefinitionServiceImpl implements ApiDefinitionService {


    @Autowired
    private ApiDefinitionMapper apiDefinitionMapper;

    @Autowired

    private ApiDefinitionRepository apiDefinitionRepository;

    @Override
    public ApiDefinitionResponseDto findByUuid(String uuid) {
        return apiDefinitionMapper.toResponseDto(apiDefinitionRepository.findByUuid(uuid));
    }

    @Override
    public List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid) {
        return apiDefinitionMapper.toResponseDtoList(apiDefinitionRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public ApiDefinitionResponseDto create(ApiDefinitionRequestDto apiDefinitionRequestDto) {

        ApiDefinition apiDefinition = apiDefinitionMapper.toEntity(apiDefinitionRequestDto);
        apiDefinitionRepository.save(apiDefinition);

        return apiDefinitionMapper.toResponseDto(apiDefinition);
    }

    @Override
    public void update(String uuid,ApiDefinitionRequestDto apiDefinitionRequestDto) {
    }

    @Override
    public void delete(String uuid) {

        apiDefinitionRepository.delete(apiDefinitionRepository.findByUuid(uuid));

    }
}
