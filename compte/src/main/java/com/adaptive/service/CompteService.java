package com.adaptive.service;

import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;

import java.util.List;

public interface CompteService {

    CompteResponseDto findByRib(Long rib);

    List<CompteResponseDto> findByBanqueUuid(String banqueUuid);

    CompteResponseDto create(CompteRequestDto compteRequestDto);

    String update(Long rib, Double amount);

    String findCustomerUuidByRib(Long rib);

    String delete(Long rib);

}
