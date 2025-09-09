package com.adaptive.repository;


import com.adaptive.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, Long> {


    Compte findByRib(Long rib);
    List<Compte> findAllByIsDeletedFalse();
    List<Compte> findByBanqueUuid(String banqueUuid);
    String findCustomerUuidByRib(Long rib);

}
