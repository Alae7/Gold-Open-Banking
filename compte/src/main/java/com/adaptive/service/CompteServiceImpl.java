package com.adaptive.service;


import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;
import com.adaptive.entity.Compte;
import com.adaptive.mapper.CompteMapper;
import com.adaptive.repository.CompteRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {


    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Override
    public CompteResponseDto findByRib(Long rib) {
        return compteMapper.toResponseDto(compteRepository.findByRib(rib));
    }

    @Override
    public List<CompteResponseDto> findByBanqueUuid(String banqueUuid) {
        return compteMapper.toResponseDtoList(compteRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public CompteResponseDto create(CompteRequestDto compteRequestDto) {
        return null;
    }

    @Override
    public String update(Long rib, Double amount) {
        return "";
    }

    @Override
    public String findCustomerUuidByRib(Long rib) {
        return compteRepository.findCustomerUuidByRib(rib);
    }

    @Override
    public String delete(Long rib) {
        Compte compte = compteRepository.findByRib(rib);
        compte.setDeleted(true);
        compteRepository.save(compte);
        return " successfully deleted ";
    }

}
