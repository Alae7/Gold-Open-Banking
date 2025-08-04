package com.adaptive.repository;

import com.adaptive.entity.Credit;
import com.adaptive.entity.CreditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByCompteRib(Long compteRib);
    Credit findByUuid(String uuid);
    List<Credit> findByStatusAndDateFinBefore(CreditStatus status, LocalDate today);

}
