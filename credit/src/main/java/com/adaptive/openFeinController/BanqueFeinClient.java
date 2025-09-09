package com.adaptive.openFeinController;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.model.BanqueResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("BANQUE-SERVICE")
public interface BanqueFeinClient {

    @GetMapping("/api/banque/{uuid}")
    public BanqueResponseDto findByUuid(@PathVariable("uuid") String uuid);

    @PostMapping("/api/apiDefinition/execute")
    public ResponseEntity<?> execute(@RequestBody ExecuteRequest executeRequest);


}
