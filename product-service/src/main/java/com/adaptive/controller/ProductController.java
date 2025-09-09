package com.adaptive.controller;

import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
import com.adaptive.dto.ProductResponseDtoV1;
import com.adaptive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public List<ProductResponseDto> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{uuid}")
    public ProductResponseDto findByUuid(@PathVariable("uuid") String uuid) {

        return productService.findByUuid(uuid);

    }

    @GetMapping("/credit/{uuid}")
    public ProductResponseDtoV1 findByUuidV1(@PathVariable("uuid") String uuid) {

        return productService.findByUuidV1(uuid);

    }

    @GetMapping("/banks")
    public List<Object> findAllBanks(){
        return productService.findAllFromBanks();
    }


    @GetMapping("/banks/{uuid}")
    public ResponseEntity<?> findByUuidFromBank(@PathVariable("uuid") String uuid ,@RequestParam String code){

        return productService.findByUuidFromBank(uuid,code);

    }


    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {

        return productService.create(productRequestDto);

    }


    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid) {

        return productService.delete(uuid);

    }


}
