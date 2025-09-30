package com.adaptive.service;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.dto.EcheanceResponseDto;
import com.adaptive.dto.Notification_CreditRequestDto;
import com.adaptive.entity.Credit;
import com.adaptive.entity.Echeance;
import com.adaptive.entity.TypeTransaction;
import com.adaptive.mapper.CreditMapper;
import com.adaptive.mapper.EcheanceMapper;
import com.adaptive.model.TransactionRequestDto;
import com.adaptive.model.TransactionResponseDto;
import com.adaptive.openFeinController.TransactionFeinClient;
import com.adaptive.repository.CreditRepository;
import com.adaptive.repository.EcheanceRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
    private EcheanceMapper echeanceMapper;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private KafkaTemplate<String, Notification_CreditRequestDto> kafkaTemplate;


    @Autowired
    private TransactionFeinClient transactionFeinClient;

    @Override
    public List<EcheanceResponseDto> findByCreditUuid(String creditUuid) {
        return echeanceMapper.toResponseDtoList(echeanceRepository.findByCreditUuid(creditUuid));
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void remboursement(){

        List<Echeance> list = echeanceRepository.findByDateEcheanceBeforeAndPayeIsFalse(LocalDate.now());

        for (Echeance echeance : list) {

            Credit credit = creditRepository.findByUuid(echeance.getCredit().getUuid());
            TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

            transactionRequestDto.setAmount(credit.getRemboursement());
            transactionRequestDto.setType(TypeTransaction.REMBOURSEMENT);
            transactionRequestDto.setSourceRib(echeance.getRib());

            TransactionResponseDto transactionResponseDto = transactionFeinClient.createTransaction(transactionRequestDto);
            echeance.setPaye(transactionResponseDto.getStatus().equals("SUCCESS"));

            echeanceRepository.save(echeance);

        }


    }
}
