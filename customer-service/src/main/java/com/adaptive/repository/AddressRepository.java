package com.adaptive.repository;

import com.adaptive.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {


    Address findByUuid(String uuid);

}
