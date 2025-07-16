package com.adaptive.mapper;


import com.adaptive.dto.CompteResponseDto;
import com.adaptive.entity.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring")
public interface CompteMapper {

    CompteMapper INSTANCE = Mappers.getMapper(CompteMapper.class);

    CompteResponseDto toResponseDto(Compte compte);

    List<CompteResponseDto> toResponseDtoList(List<Compte> comptes);


}
