package com.adaptive.mapper;


import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.dto.ProductResponseDtoV1;
import com.adaptive.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toResponseDto(Product product);

    ProductResponseDtoV1 toResponseDtoV1(Product product);

    Product toProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> toResponseDtoList(List<Product> products);


}
