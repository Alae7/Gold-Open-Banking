package com.adaptive.openFeinController;

import com.adaptive.config.ExecuteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("BANQUE-SERVICE")
public interface BanqueFeinClient {

    @PostMapping("/api/apiDefinition/execute")
    public ResponseEntity<?> execute(@RequestBody ExecuteRequest executeRequest);

    @GetMapping("/api/apiDefinition/execute")
    public List<Object> execute();

}
