package com.adaptive.service;

import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.dto.ProductResponseDtoV1;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductResponseDto findByUuid(String uuid);

    ProductResponseDtoV1 findByUuidV1(String uuid);

    ResponseEntity<?> findByUuidFromBank(String uuid, String code);

    List<ProductResponseDto> findAll();

    List<Object> findAllFromBanks();

    ProductResponseDto create(ProductRequestDto productRequestDto);

    String delete(String uuid);

}
