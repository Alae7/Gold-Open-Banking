package com.adaptive.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {

    private double amount;
    private LocalDateTime createDateTime;
    private String transactionType;



}
