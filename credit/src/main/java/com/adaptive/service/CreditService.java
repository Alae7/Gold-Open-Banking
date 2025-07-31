package com.adaptive.service;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.entity.CreditStatus;

import java.util.List;

public interface CreditService {

    CreditResponseDto findByUuid(String uuid);

    List<CreditResponseDto> findByCompteRib(Long rib);

    CreditResponseDto create(CreditRequestDto creditRequestDto);

    void changeStatus(String uuid, CreditStatus creditStatus);

}
