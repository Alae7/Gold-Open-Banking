package com.adaptive.repository;

import com.adaptive.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {


    Customer findByUuid(String uuid);
    List<Customer> findAllByIsDeletedFalse();
    List<Customer> findByAddress_City(String city);
    Customer findByEmail(String email);

}
