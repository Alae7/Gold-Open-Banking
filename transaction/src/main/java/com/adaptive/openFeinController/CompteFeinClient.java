package com.adaptive.openFeinController;

import com.adaptive.model.CompteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("COMPTE")
public interface CompteFeinClient {

    @GetMapping("/api/compte/{rib}")
    public CompteResponseDto findByRib(@PathVariable("rib") Long rib);

    @PutMapping("/api/compte/{rib}")
    public String versement(@PathVariable("rib") Long rib, @RequestBody Double amount);

}
