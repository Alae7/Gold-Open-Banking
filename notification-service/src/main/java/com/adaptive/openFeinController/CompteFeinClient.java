package com.adaptive.openFeinController;

import com.adaptive.model.CompteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("COMPTE")
public interface CompteFeinClient {

    @GetMapping("/api/compte/customer/{rib}")
    public String findCustomerUuidByRib(@PathVariable("rib") Long rib);


    @GetMapping("/api/compte/{rib}")
    public CompteResponseDto findByRib(@PathVariable("rib") Long rib);

}
