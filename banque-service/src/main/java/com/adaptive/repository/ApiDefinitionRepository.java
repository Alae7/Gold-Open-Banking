package com.adaptive.repository;

import com.adaptive.entity.ApiDefinition;
import com.adaptive.entity.NameApi;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApiDefinitionRepository extends MongoRepository<ApiDefinition, String> {


    ApiDefinition findByUuid(String uuid);
    List<ApiDefinition> findByBanqueUuid(String banqueUuid);
    ApiDefinition findByBanqueUuidAndName(String banqueUuid, NameApi name);

}
