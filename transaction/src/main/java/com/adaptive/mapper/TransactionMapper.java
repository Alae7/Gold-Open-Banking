package com.adaptive.mapper;


import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.dto.TransactionResponseDto;
import com.adaptive.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring",uses = {TransactionMapper.class})
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionResponseDto toResponseDto(Transaction transaction);

    Transaction toTransaction(TransactionRequestDto transactionRequestDto);

    List<TransactionResponseDto> toResponseDtoList(List<Transaction> transactions);


}
