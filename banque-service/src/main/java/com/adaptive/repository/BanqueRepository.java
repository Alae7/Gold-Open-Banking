package com.adaptive.repository;

import com.adaptive.entity.Banque;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BanqueRepository extends MongoRepository<Banque, String> {


    Banque findByUuid(String uuid);
    Banque findByCode(String code);
    String findCodeByUuid(String uuid);
}
