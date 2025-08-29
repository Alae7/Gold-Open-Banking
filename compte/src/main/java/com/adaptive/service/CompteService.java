package com.adaptive.service;

import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;
import com.adaptive.model.Transaction;

import java.util.List;

public interface CompteService {

    CompteResponseDto findByRib(Long rib);

    List<CompteResponseDto> findByBanqueUuid(String banqueUuid);

    boolean create(CompteRequestDto compteRequestDto);

    String update(Long rib, Transaction transaction);

    String activate(Long rib);

    String deactivate(Long rib);

    String findCustomerUuidByRib(Long rib);

    String delete(Long rib);

}
