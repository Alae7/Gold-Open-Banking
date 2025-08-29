package com.adaptive.openFeinController;

import com.adaptive.dto.CompteRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("COMPTE")
public interface CompteFeinClient {

    @PostMapping("/api/compte")
    public boolean createCompte(@RequestBody CompteRequestDto compteRequestDto);

}
