package com.adaptive.openFeinController;

import com.adaptive.model.Banque;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("BANQUE-SERVICE")
public interface BanqueFeinClient {

    @GetMapping("/api/banque/code/{uuid}")
    public Banque getBanqueModel(@PathVariable("uuid") String uuid);


}
