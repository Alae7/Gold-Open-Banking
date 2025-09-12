package com.adaptive.repository;


import com.adaptive.entity.Anomaly;
import com.adaptive.entity.Transaction;
import com.adaptive.entity.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {


    List<Anomaly> findByTraite(boolean traite);

    Anomaly getAnomalyById(Long id);

}
