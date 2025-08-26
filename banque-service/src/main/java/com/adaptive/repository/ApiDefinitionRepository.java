package com.adaptive.repository;

import com.adaptive.entity.ApiDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface ApiDefinitionRepository extends MongoRepository<ApiDefinition, String> {


    ApiDefinition findByUuid(String uuid);
    List<ApiDefinition> findByBanqueUuid(String banqueUuid);
    List<ApiDefinition> findByMethod(HttpMethod method);

}
