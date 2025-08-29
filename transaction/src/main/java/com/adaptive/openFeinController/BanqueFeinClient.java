package com.adaptive.openFeinController;

import com.adaptive.config.ExecuteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("BANQUE-SERVICE")
public interface BanqueFeinClient {

    @PostMapping("/api/apiDefinition/execute")
    public ResponseEntity<?> execute(@RequestBody ExecuteRequest executeRequest);

}
