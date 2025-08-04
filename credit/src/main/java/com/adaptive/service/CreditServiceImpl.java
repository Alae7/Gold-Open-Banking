package com.adaptive.service;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.CreditStatus;
import com.adaptive.entity.Echeance;
import com.adaptive.mapper.CreditMapper;
import com.adaptive.repository.CreditRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        if(utils.kycCredit(creditRequestDto)){
            // if scoring is good
            this.changeStatus(credit.getUuid(),CreditStatus.APPROUVE);
            this.changeStatus(credit.getUuid(),CreditStatus.EN_COURS);
            return creditMapper.toResponseDto(credit);
        }
        // if scoring or kyc is bad
        this.changeStatus(credit.getUuid(),CreditStatus.REFUSE);
        return creditMapper.toResponseDto(credit);
    }

    @Override
    public void changeStatus(String uuid, CreditStatus creditStatus) {

        Credit credit = creditRepository.findByUuid(uuid);
        credit.setStatus(creditStatus);
        creditRepository.save(credit);

        if (credit.getStatus().equals(CreditStatus.APPROUVE)){
            utils.createEcheance(credit);
        }

    }


    @Scheduled(cron = "0 0 1 1 * *")
    public void updateTermineCredits() {
        LocalDate today = LocalDate.now();

        List<Credit> creditsToCheck = creditRepository.findByStatusAndDateFinBefore(CreditStatus.EN_COURS, today);

        for (Credit credit : creditsToCheck) {
            boolean allPaid = credit.getEcheances()
                    .stream()
                    .allMatch(Echeance::isPaye);

            if (allPaid) {
                credit.setStatus(CreditStatus.TERMINE);
                creditRepository.save(credit);
                System.out.println("✅ Credit ID " + credit.getId() + " marked as TERMINÉ.");
            }
        }
    }


}
