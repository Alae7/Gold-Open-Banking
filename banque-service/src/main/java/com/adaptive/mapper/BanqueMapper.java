package com.adaptive.mapper;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;
import com.adaptive.entity.Banque;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {ApiDefinitionMapper.class})
public interface BanqueMapper {

    BanqueMapper INSTANCE = Mappers.getMapper(BanqueMapper.class);

    BanqueResponseDto toResponseDto(Banque banque);

    Banque toEntity(BanqueRequestDto banqueRequestDto);

    List<BanqueResponseDto> toResponseDtoList(List<Banque> banques);


}
