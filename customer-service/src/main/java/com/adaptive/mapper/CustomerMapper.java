package com.adaptive.mapper;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponseDto toResponseDto(Customer customer);

    Customer toEntity(CustomerRequestDto customerRequestDto);

    List<CustomerResponseDto> toResponseDtoList(List<Customer> customers);


}
