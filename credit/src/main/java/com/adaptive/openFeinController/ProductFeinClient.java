package com.adaptive.openFeinController;


import com.adaptive.model.ProductResponseDtoV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeinClient {

    @GetMapping("/api/product/credit/{uuid}")
    public ProductResponseDtoV1 findByUuidV1(@PathVariable("uuid") String uuid);


}
