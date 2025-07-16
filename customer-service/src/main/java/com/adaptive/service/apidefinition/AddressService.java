package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.AddressResponseDto;
import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.entity.Address;

import java.util.List;

public interface AddressService {

    AddressResponseDto findByUuid(String uuid);

    List<AddressResponseDto> findAll();

    Address create(CustomerRequestDto customerRequestDto);

    void update(CustomerRequestDto customerRequestDto, String uuid);

    void delete(String uuid);


}
