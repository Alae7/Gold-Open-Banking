package com.adaptive.service;

import com.adaptive.dto.EcheanceResponseDto;

import java.util.List;

public interface EcheanceService {


    List<EcheanceResponseDto> findByCreditUuid(String creditUuid);

}
