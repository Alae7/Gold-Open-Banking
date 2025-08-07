package com.adaptive.mapper;


import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {ProductMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toResponseDto(Product product);

    Product toProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> toResponseDtoList(List<Product> products);


}
