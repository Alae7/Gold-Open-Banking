package com.adaptive.service;



import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.entity.Product;
import com.adaptive.mapper.ProductMapper;
import com.adaptive.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ProductResponseDto findByUuid(String uuid) {
        return productMapper.toResponseDto(productRepository.findByUuid(uuid));
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productMapper.toResponseDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductResponseDto> findByCompteType(String compteType) {
        return productMapper.toResponseDtoList(productRepository.findByCompteType(compteType));
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequestDto) {

        Product product = productMapper.toProduct(productRequestDto);
        productRepository.save(product);
        return productMapper.toResponseDto(product);
    }

    @Override
    public String delete(String uuid) {

        Product product = productRepository.findByUuid(uuid);
        product.setDeleted(true);
        return " deleted product successfully ";
    }
}
