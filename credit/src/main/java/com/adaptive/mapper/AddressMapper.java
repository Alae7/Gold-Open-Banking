package com.adaptive.mapper;


import com.adaptive.dto.apidefinition.AddressResponseDto;
import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {AddressMapper.class})
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressResponseDto toResponseDto(Address address);

    Address toEntity(CustomerRequestDto customerRequestDto);

    List<AddressResponseDto> toResponseDtoList(List<Address> addresses);


}
