package com.adaptive.mapper;


import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.entity.ApiDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {ApiDefinitionMapperHelper.class})
public interface ApiDefinitionMapper {

    ApiDefinitionMapper INSTANCE = Mappers.getMapper(ApiDefinitionMapper.class);

    @Mapping(source = "method",target = "method",qualifiedByName = "toString")
    ApiDefinitionResponseDto toResponseDto(ApiDefinition apiDefinition);

    ApiDefinition toEntity(ApiDefinitionRequestDto apiDefinitionRequestDto);

    List<ApiDefinitionResponseDto> toResponseDtoList(List<ApiDefinition> apiDefinitions);


}
