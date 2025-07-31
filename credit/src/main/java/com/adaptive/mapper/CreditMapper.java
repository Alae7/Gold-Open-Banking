package com.adaptive.mapper;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {EcheanceMapper.class})
public interface CreditMapper {

    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    CreditResponseDto toResponseDto(Credit credit);

    List<CreditResponseDto> toResponseDtoList(List<Credit> credits);


}
