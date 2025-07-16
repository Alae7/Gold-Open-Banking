package com.adaptive.openFeinController;

import com.adaptive.model.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CUSTOMER-SERVICE")
public interface CustomerFiegnClient {

    @GetMapping("/api/customer/{uuid}")
    public CustomerResponseDto findCustomerById(@PathVariable("uuid") String uuid);

}
