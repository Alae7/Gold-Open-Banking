package com.adaptive.service;

import com.adaptive.dto.EcheanceResponseDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.Echeance;
import com.adaptive.repository.CreditRepository;
import com.adaptive.repository.EcheanceRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EcheanceServiceImpl implements EcheanceService {

    @Autowired
    private EcheanceRepository echeanceRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private Utils utils;

    @Override
    public List<EcheanceResponseDto> findByCreditUuid(String creditUuid) {
        return List.of();
    }

    @Override
    public void create(Credit credit) {

    }

    @Scheduled(cron = "0 0 1 * * *")
    public void remboursement(){

        List<Echeance> list = echeanceRepository.findByDateEcheanceBeforeAndPayeIsFalse(LocalDate.now());

        for (Echeance echeance : list) {

            Credit credit = creditRepository.findByUuid(echeance.getCreditUuid());

        }


    }
}
