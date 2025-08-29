package com.adaptive.service;

import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductResponseDto findByUuid(String uuid);

    ResponseEntity<?> findByUuidFromBank(String uuid, String code);

    List<ProductResponseDto> findAll();

    List<Object> findAllFromBanks();

    List<ProductResponseDto> findByCompteType(String compteType);

    List<Object> findByCompteTypeFromBanks(String compteType);

    ProductResponseDto create(ProductRequestDto productRequestDto);

    String delete(String uuid);

}
