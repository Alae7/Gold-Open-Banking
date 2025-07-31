package com.adaptive.service;

import com.adaptive.dto.EcheanceResponseDto;
import com.adaptive.entity.Credit;

import java.util.List;

public interface EcheanceService {


    List<EcheanceResponseDto> findByCreditUuid(String creditUuid);

    void create(Credit credit);

}
