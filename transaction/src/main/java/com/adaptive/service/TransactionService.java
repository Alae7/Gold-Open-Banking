package com.adaptive.service;

import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto findByUuid(String uuid);

    List<TransactionResponseDto> findAllBySourceRib(Long sourceRib);

    List<TransactionResponseDto> findAllByTargetRib(Long sourceRib);

    List<TransactionResponseDto> findAll();

    List<TransactionResponseDto> findByType(String type);

    TransactionResponseDto create(TransactionRequestDto transactionRequestDto);

    String delete(String uuid);



}
