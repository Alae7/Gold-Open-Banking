package com.adaptive.service;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;

import com.adaptive.entity.Banque;
import com.adaptive.model.BanqueModel;
import com.adaptive.repository.BanqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {

    @Autowired
    private BanqueRepository banqueRepository;

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

    @Override
    public BanqueModel getBanqueModel(String uuid) {

        BanqueModel banqueModel = new BanqueModel();
        Banque banque = banqueRepository.findByUuid(uuid);
        banqueModel.setBanqueUuid(uuid);
        banqueModel.setCodeBanque(banque.getCode());
        return banqueModel;
    }
}
