package com.adaptive.openFeinController;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("COMPTE")
public interface CompteFeinClient {

    @GetMapping("/api/compte/customer/{rib}")
    public String findCustomerUuidByRib(@PathVariable("rib") Long rib);

}
