package com.adaptive.service;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;

import com.adaptive.entity.Banque;
import com.adaptive.mapper.BanqueMapper;
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

    @Autowired
    private BanqueMapper  banqueMapper;

    @Override
    public BanqueResponseDto findByUuid(String uuid) {
        return banqueMapper.toResponseDto(banqueRepository.findByUuid(uuid));
    }

    @Override
    public List<BanqueResponseDto> findAll() {
        return banqueMapper.toResponseDtoList(banqueRepository.findAll());
    }

    @Override
    public BanqueResponseDto create(BanqueRequestDto banqueRequestDto) {

        Banque banque = banqueMapper.toEntity(banqueRequestDto);
        banqueRepository.save(banque);

        return banqueMapper.toResponseDto(banque);
    }

    @Override
    public BanqueResponseDto update(BanqueRequestDto banqueRequestDto, String uuid) {
        return null;
    }

    @Override
    public String delete(String uuid) {

        Banque banque = banqueRepository.findByUuid(uuid);
        banque.setDeleted(true);
        banqueRepository.save(banque);

        return " banque has been deleted ";
    }


}
