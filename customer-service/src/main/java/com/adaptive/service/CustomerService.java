package com.adaptive.service;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto findByUuid(String uuid);

    List<CustomerResponseDto> findAll();

    String  create(CustomerRequestDto customerRequestDto);

    String delete(String uuid);

    List<CustomerResponseDto> findAllByDeletedIsFalse();

    String findUuidByEmail(String email);

    List<CustomerResponseDto> findByCity(String city);

    CustomerResponseDto findByCin(String cin);

    List<CustomerResponseDto> findByBankUuid(String bankUuid);

}
