package com.adaptive.openFeinController;


import com.adaptive.model.TransactionRequestDto;
import com.adaptive.model.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("TRANSACTION")
public interface TransactionFeinClient {

    @PostMapping("/api/transaction/{uuid}")
    public TransactionResponseDto createTransaction(@RequestBody TransactionRequestDto transactionRequestDto);


}
