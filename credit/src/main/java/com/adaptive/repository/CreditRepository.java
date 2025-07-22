package com.adaptive.repository;

import com.adaptive.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByCustomerUuid(String customerUuid);
    Credit findByUuid(String uuid);

}
