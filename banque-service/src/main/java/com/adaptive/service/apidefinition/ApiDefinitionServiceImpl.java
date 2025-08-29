package com.adaptive.service.apidefinition;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.entity.ApiDefinition;
import com.adaptive.entity.Banque;
import com.adaptive.entity.NameApi;
import com.adaptive.mapper.ApiDefinitionMapper;
import com.adaptive.repository.ApiDefinitionRepository;
import com.adaptive.repository.BanqueRepository;
import com.adaptive.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@Transactional
public class ApiDefinitionServiceImpl implements ApiDefinitionService {


    @Autowired
    private ApiDefinitionMapper apiDefinitionMapper;

    @Autowired

    private ApiDefinitionRepository apiDefinitionRepository;

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private Utils utils;

    @Override
    public ApiDefinitionResponseDto findByUuid(String uuid) {
        return apiDefinitionMapper.toResponseDto(apiDefinitionRepository.findByUuid(uuid));
    }

    @Override
    public List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid) {
        return apiDefinitionMapper.toResponseDtoList(apiDefinitionRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public String create(List<ApiDefinitionRequestDto> apiDefinitionRequestDtos) {

        Banque banque = banqueRepository.findByUuid(apiDefinitionRequestDtos.get(0).getBanqueUuid());
        List<ApiDefinition>  apiDefinitions = new ArrayList<>();
        try {

            for (ApiDefinitionRequestDto apiDefinitionRequestDto : apiDefinitionRequestDtos) {

                ApiDefinition apiDefinition = apiDefinitionMapper.toEntity(apiDefinitionRequestDto);
                apiDefinition = apiDefinitionRepository.save(apiDefinition);
                apiDefinitions.add(apiDefinition);

            }

            banque.setApiDefinitions(apiDefinitions);
            banqueRepository.save(banque);

            return "Success";

        }catch (Exception e){

            return "Error";

        }

    }

    @Override
    public void update(String uuid, String url) {

        ApiDefinition apiDefinition = apiDefinitionRepository.findByUuid(uuid);
        apiDefinition.setUrl(url);
        apiDefinitionRepository.save(apiDefinition);

    }

    @Override
    public void delete(String uuid) {

        apiDefinitionRepository.delete(apiDefinitionRepository.findByUuid(uuid));

    }

    @Override
    public ResponseEntity<?> executeApi(ExecuteRequest executeRequest) {

        Banque banque = banqueRepository.findByCode(executeRequest.getBanqueCode());
        ApiDefinition apiDefinition = apiDefinitionRepository.findByBanqueUuidAndName(banque.getUuid(),executeRequest.getNameApi());

        switch (apiDefinition.getMethod().toString()) {

            case "POST"  -> {
                return utils.postExecution(apiDefinition.getUrl(),executeRequest.getRequestBody());
            }
            case "PUT" -> {
                return utils.putExecution(apiDefinition.getUrl(),executeRequest.getPathParams(),executeRequest.getRequestBody());
            }
            case "DELETE" -> {
                return utils.deleteExecution(apiDefinition.getUrl(),executeRequest.getPathParams());
            }

            case "GET" ->{

                if(apiDefinition.getName().equals(NameApi.GET_ALL_PRODUCT)){
                    return utils.getExecution(apiDefinition.getUrl());
                }else {
                    return utils.getExecution(apiDefinition.getUrl(),executeRequest.getPathParams());
                }
            }

            default -> {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                        .body("HTTP method not supported: " + apiDefinition.getMethod().toString());
            }
        }

    }

    @Override
    public List<Object> getFromAllApi() {

        List<Banque> banques =  banqueRepository.findAll();
        List<Object> results = new ArrayList<>();


        for (Banque banque : banques) {
            try {
                ApiDefinition apiDefinition = apiDefinitionRepository
                        .findByBanqueUuidAndName(banque.getUuid(), NameApi.GET_ALL_PRODUCT);

                if (apiDefinition == null || apiDefinition.getUrl() == null) {
                    log.warn("No API definition found for bank: {}", banque.getName());
                    continue;
                }

                ResponseEntity<?> response = utils.getExecution(apiDefinition.getUrl());

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    results.add(response.getBody()); // ✅ Extract data here
                } else {
                    log.error("❌ Failed to fetch data for bank {}: {}", banque.getName(), response.getStatusCode());
                }

            } catch (Exception e) {
                log.error("Error while fetching products for bank {}: {}", banque.getName(), e.getMessage());
            }
        }
        return results;
    }


    @Override
    public List<Object> getFromAllApi(Map<String, String> pathParams){


        List<Banque> banques =  banqueRepository.findAll();
        List<Object> results = new ArrayList<>();


        for (Banque banque : banques) {
            try {
                ApiDefinition apiDefinition = apiDefinitionRepository
                        .findByBanqueUuidAndName(banque.getUuid(), NameApi.GET_PRODUCT_BY_ACCOUNT_TYPE);

                if (apiDefinition == null || apiDefinition.getUrl() == null) {
                    log.warn("No API definition found for bank: {}", banque.getName());
                    continue;
                }

                ResponseEntity<?> response = utils.getExecution(apiDefinition.getUrl(),pathParams);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    results.add(response.getBody()); // ✅ Extract data here
                } else {
                    log.error("❌ Failed to fetch data for bank {}: {}", banque.getName(), response.getStatusCode());
                }

            } catch (Exception e) {
                log.error("Error while fetching products for bank {}: {}", banque.getName(), e.getMessage());
            }
        }
        return results;

    }


}
