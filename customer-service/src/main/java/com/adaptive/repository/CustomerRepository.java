package com.adaptive.repository;

import com.adaptive.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {


        Customer findByCin(String cin);

        Customer findByUuid(String uuid);

        List<Customer> findByCity(String city);

        Customer findByEmail(String email);

        List<Customer> findAllByDeletedIsFalse();

}
