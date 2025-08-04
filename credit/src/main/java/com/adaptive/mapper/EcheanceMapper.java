package com.adaptive.mapper;




import com.adaptive.dto.EcheanceResponseDto;
import com.adaptive.entity.Echeance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring")
public interface EcheanceMapper {

    EcheanceMapper INSTANCE = Mappers.getMapper(EcheanceMapper.class);

    EcheanceResponseDto toResponseDto(Echeance echeance);

    List<EcheanceResponseDto> toResponseDtoList(List<Echeance> echeances);



}
