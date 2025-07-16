package com.adaptive.service;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {


    @Override
    public BanqueResponseDto findByUuid(String uuid) {
        return null;
    }

    @Override
    public List<BanqueResponseDto> findAll() {
        return List.of();
    }

    @Override
    public BanqueResponseDto create(BanqueRequestDto banqueRequestDto) {
        return null;
    }

    @Override
    public BanqueResponseDto update(BanqueRequestDto banqueRequestDto, String uuid) {
        return null;
    }

    @Override
    public String delete(String uuid) {
        return "";
    }
}
