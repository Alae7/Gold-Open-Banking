package com.adaptive.service;



import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.dto.ProductResponseDtoV1;
import com.adaptive.entity.Product;
import com.adaptive.mapper.ProductMapper;
import com.adaptive.openFeinController.BanqueFeinClient;
import com.adaptive.repository.ProductRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BanqueFeinClient banqueFeinClient;


    @Override
    public ProductResponseDto findByUuid(String uuid) {
        return productMapper.toResponseDto(productRepository.findByUuid(uuid));
    }

    @Override
    public ProductResponseDtoV1 findByUuidV1(String uuid) {
        return productMapper.toResponseDtoV1(productRepository.findByUuid(uuid));
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
    public ProductResponseDto create(ProductRequestDto productRequestDto) {

        Product product = productMapper.toProduct(productRequestDto);
        product.setStatut(false);
        product.setRemboursement(Utils.calculateMontantRembourse(productRequestDto));
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
