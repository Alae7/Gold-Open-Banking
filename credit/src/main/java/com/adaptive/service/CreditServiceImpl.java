package com.adaptive.service;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.CreditStatus;
import com.adaptive.mapper.CreditMapper;
import com.adaptive.repository.CreditRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private Utils utils;

    @Autowired
    private CreditRepository creditRepository;

    @Override
    public CreditResponseDto findByUuid(String uuid) {
        return creditMapper.toResponseDto(creditRepository.findByUuid(uuid));
    }

    @Override
    public List<CreditResponseDto> findByCompteRib(Long rib) {
        return creditMapper.toResponseDtoList(creditRepository.findByCompteRib(rib));
    }

    @Override
    public CreditResponseDto create(CreditRequestDto creditRequestDto) {

        Credit credit = utils.create(creditRequestDto);
        creditRepository.save(credit);

        return creditMapper.toResponseDto(credit);

    }

    @Override
    public void changeStatus(String uuid, CreditStatus creditStatus) {

        Credit credit = creditRepository.findByUuid(uuid);
        credit.setStatus(creditStatus);
        creditRepository.save(credit);

    }
}
