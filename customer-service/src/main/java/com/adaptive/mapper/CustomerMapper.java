package com.adaptive.mapper;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring", uses = {CustomerMapperHelper.class})
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "image.url", target = "image")
    CustomerResponseDto toResponseDto(Customer customer);

    @Mapping(source = "image",target = "image",qualifiedByName = "createImage")
    Customer toEntity(CustomerRequestDto customerRequestDto);

    List<CustomerResponseDto> toResponseDtoList(List<Customer> customers);


}
