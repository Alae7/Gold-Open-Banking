package com.adaptive.service;


import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.dto.TransactionResponseDto;
import com.adaptive.entity.Transaction;
import com.adaptive.mapper.TransactionMapper;
import com.adaptive.model.NotificationRequestDto;
import com.adaptive.repository.TransactionRepository;
import com.adaptive.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private KafkaTemplate<String, NotificationRequestDto> kafkaTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;


    @Override
    public TransactionResponseDto findByUuid(String uuid) {
        return transactionMapper.toResponseDto(transactionRepository.findByUuid(uuid));
    }

    @Override
    public List<TransactionResponseDto> findAllBySourceRib(Long sourceRib) {
        return transactionMapper.toResponseDtoList(transactionRepository.findAllBySourceRib(sourceRib));
    }

    @Override
    public List<TransactionResponseDto> findAllByTargetRib(Long targetRib) {
        return transactionMapper.toResponseDtoList(transactionRepository.findAllByTargetRib(targetRib));
    }

    @Override
    public List<TransactionResponseDto> findAll() {
        return transactionMapper.toResponseDtoList(transactionRepository.findAll());
    }

    @Override
    public List<TransactionResponseDto> findByType(String type) {
        return transactionMapper.toResponseDtoList(transactionRepository.findByType(type));
    }

    @Override
    public TransactionResponseDto create(TransactionRequestDto transactionRequestDto) {

        Transaction transaction = transactionMapper.toTransaction(transactionRequestDto);
        String result = Utils.doTransaction(transactionRequestDto);
        switch (result) {
            case "ACCEPTED" -> transaction.setStatut("ACCEPTED");
            case "REFUSED" -> transaction.setStatut("REFUSED");
            default -> throw new IllegalArgumentException("Invalid Transaction");
        }
        transactionRepository.save(transaction);

        NotificationRequestDto notificationRequestDto = Utils.createNotification(transaction);

        kafkaTemplate.send("notification_topic",notificationRequestDto.getNotificationType() ,notificationRequestDto);
        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public String delete(String uuid) {
        Transaction transaction = transactionRepository.findByUuid(uuid);
        transaction.setDeleted(true);
        return " transaction deleted successfully ";
    }
}
