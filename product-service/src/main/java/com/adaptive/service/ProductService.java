package com.adaptive.service;

import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto findByUuid(String uuid);

    List<ProductResponseDto> findAll();

    List<ProductResponseDto> findByCompteType(String compteType);

    ProductResponseDto create(ProductRequestDto productRequestDto);

    String delete(String uuid);

}
