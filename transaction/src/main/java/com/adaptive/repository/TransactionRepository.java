package com.adaptive.repository;


import com.adaptive.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Transaction findByUuid(String uuid);
    List<Transaction> findAllBySourceRib(Long rib);
    List<Transaction> findAllByTargetRib(Long rib);
    List<Transaction> findByType(String type);

}
