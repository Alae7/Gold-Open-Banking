package com.adaptive.service;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto findByUuid(String uuid);

    List<CustomerResponseDto> findAll();

    CustomerResponseDto create(CustomerRequestDto customerRequestDto);

    String delete(String uuid);

    List<CustomerResponseDto> findAllByDeletedIsFalse();

    String findUuidByEmail(String email);

    List<CustomerResponseDto> findByCity(String city);

}
