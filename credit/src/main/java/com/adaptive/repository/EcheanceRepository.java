package com.adaptive.repository;

import com.adaptive.entity.Echeance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcheanceRepository extends JpaRepository<Echeance, Long> {

    List<Echeance> findByCreditUuid(String creditUuid);
    Echeance findByUuid(String uuid);

}
