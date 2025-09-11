package com.adaptive.service;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;
import com.adaptive.entity.Banque;

import java.util.List;

public interface BanqueService {

    BanqueResponseDto findByUuid(String uuid);

    List<BanqueResponseDto> findAll();

    BanqueResponseDto create(BanqueRequestDto banqueRequestDto);

    BanqueResponseDto update(BanqueRequestDto banqueRequestDto, String uuid);

    String delete(String uuid);


    BanqueResponseDto findCodeByUuid(String uuid);
}
