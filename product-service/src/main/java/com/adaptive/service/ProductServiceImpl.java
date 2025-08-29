package com.adaptive.service;



import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.entity.Product;
import com.adaptive.mapper.ProductMapper;
import com.adaptive.openFeinController.BanqueFeinClient;
import com.adaptive.repository.ProductRepository;
import com.adaptive.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BanqueFeinClient banqueFeinClient;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public ProductResponseDto findByUuid(String uuid) {
        return productMapper.toResponseDto(productRepository.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<?> findByUuidFromBank(String uuid, String code){

        ExecuteRequest request = Utils.createExecuteRequest(uuid,code);

        return banqueFeinClient.execute(request);

    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productMapper.toResponseDtoList(productRepository.findAll());
    }

    @Override
    public List<Object> findAllFromBanks() {
        return banqueFeinClient.execute();
    }

    @Override
    public List<ProductResponseDto> findByCompteType(String compteType) {
        return productMapper.toResponseDtoList(productRepository.findByCompteType(compteType));
    }

    @Override
    public List<Object> findByCompteTypeFromBanks(String compteType){

        Map<String, String> pathParams = Map.of("compteType", compteType);
        return banqueFeinClient.execute(pathParams);
    }

    @Override
    public ProductResponseDto create(ProductRequestDto productRequestDto) {

        Product product = productMapper.toProduct(productRequestDto);
        product = productRepository.save(product);
        ExecuteRequest executeRequest = Utils.createExecuteRequest(productMapper.toResponseDto(product));
        executeRequest.setBanqueCode(productRequestDto.getCodeBank());
        banqueFeinClient.execute(executeRequest);
        return productMapper.toResponseDto(product);
    }

    @Override
    public String delete(String uuid) {

        try {
            Product product = productRepository.findByUuid(uuid);
            product.setDeleted(true);
            return " deleted product successfully ";
        }catch (Exception e){
            return e.getMessage();
        }


    }
}
