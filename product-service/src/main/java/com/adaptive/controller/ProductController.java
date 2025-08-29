package com.adaptive.controller;

import com.adaptive.dto.ProductRequestDto;
import com.adaptive.dto.ProductResponseDto;
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

    @GetMapping("/banks")
    public List<Object> findAllBanks(){
        return productService.findAllFromBanks();
    }


    @GetMapping("/banks/{uuid}")
    public ResponseEntity<?> findByUuidFromBank(@PathVariable("uuid") String uuid ,@RequestBody String code){

        return productService.findByUuidFromBank(uuid,code);

    }

    @GetMapping("/banks/compte_type/{type}")
    public List<Object> findByCompteTypeFromBanks(@PathVariable("type") String type){
        return productService.findByCompteTypeFromBanks(type);
    }


    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {

        return productService.create(productRequestDto);

    }


    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid) {

        return productService.delete(uuid);

    }

    @GetMapping("/compte/{compteType}")
    public List<ProductResponseDto> findByCompteType(@PathVariable("compteType") String compteType) {
        return productService.findByCompteType(compteType);
    }

}
