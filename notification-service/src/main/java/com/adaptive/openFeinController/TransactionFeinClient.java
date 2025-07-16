package com.adaptive.openFeinController;

import com.adaptive.model.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRANSACTION")
public interface TransactionFeinClient {

    @GetMapping("/api/transaction/{uuid}")
    public TransactionResponseDto findByUuid(@PathVariable("uuid") String uuid);

}
