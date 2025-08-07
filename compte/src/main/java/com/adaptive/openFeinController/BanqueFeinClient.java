package com.adaptive.openFeinController;

import com.adaptive.model.BanqueResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("BANQUE-SERVICE")
public interface BanqueFeinClient {

    @GetMapping("/api/banque/{uuid}")
    public BanqueResponseDto findByUuid(@PathVariable("uuid") String uuid);


}
