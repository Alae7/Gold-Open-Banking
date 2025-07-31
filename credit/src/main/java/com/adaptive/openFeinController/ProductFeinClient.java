package com.adaptive.openFeinController;


import com.adaptive.model.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeinClient {

    @GetMapping("/api/product/{uuid}")
    public ProductResponseDto findByUuid(@PathVariable("uuid") String uuid);


}
