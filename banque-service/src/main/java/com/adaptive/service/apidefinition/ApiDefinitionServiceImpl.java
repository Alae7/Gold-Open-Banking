package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.entity.ApiDefinition;
import com.adaptive.entity.Banque;
import com.adaptive.entity.Headers;
import com.adaptive.entity.Parameter;
import com.adaptive.mapper.ApiDefinitionMapper;
import com.adaptive.repository.ApiDefinitionRepository;
import com.adaptive.repository.BanqueRepository;
import org.springframework.http.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApiDefinitionServiceImpl implements ApiDefinitionService {


    @Autowired
    private ApiDefinitionMapper apiDefinitionMapper;

    @Autowired

    private ApiDefinitionRepository apiDefinitionRepository;

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ApiDefinitionResponseDto findByUuid(String uuid) {
        return apiDefinitionMapper.toResponseDto(apiDefinitionRepository.findByUuid(uuid));
    }

    @Override
    public List<ApiDefinitionResponseDto> findByBanqueUuid(String banqueUuid) {
        return apiDefinitionMapper.toResponseDtoList(apiDefinitionRepository.findByBanqueUuid(banqueUuid));
    }

    @Override
    public ApiDefinitionResponseDto create(ApiDefinitionRequestDto apiDefinitionRequestDto) {

        ApiDefinition apiDefinition = apiDefinitionMapper.toEntity(apiDefinitionRequestDto);
        apiDefinition.setHeader(apiDefinitionRequestDto.getHeaders());
        apiDefinition.setParams(apiDefinitionRequestDto.getQueryParams());
        apiDefinition = apiDefinitionRepository.save(apiDefinition);

        Banque banque = banqueRepository.findByUuid(apiDefinition.getBanqueUuid());
        List<ApiDefinition> apiDefinitions = banque.getApiDefinitions();
        apiDefinitions.add(apiDefinition);
        banque.setApiDefinitions(apiDefinitions);
        banqueRepository.save(banque);

        return apiDefinitionMapper.toResponseDto(apiDefinition);
    }

    @Override
    public List<ApiDefinitionResponseDto> findByMethod(HttpMethod httpMethod) {
        return apiDefinitionMapper.toResponseDtoList(apiDefinitionRepository.findByMethod(httpMethod));
    }

    @Override
    public void update(String uuid,ApiDefinitionRequestDto apiDefinitionRequestDto) {

        ApiDefinition apiDefinition = apiDefinitionRepository.findByUuid(uuid);



    }

    @Override
    public void delete(String uuid) {

        apiDefinitionRepository.delete(apiDefinitionRepository.findByUuid(uuid));

    }

    @Override
    public ResponseEntity<?> executeApi(String uuid , Map<String, String> pathParams,  Map<String, String> queryParams ,Map<String, String> requestBody) {

        ApiDefinition apiDefinition = apiDefinitionRepository.findByUuid(uuid);
        String url = apiDefinition.getUrl();
        String body = apiDefinition.getBodyJson();
        HttpHeaders httpHeaders = new HttpHeaders();
        // Build headers dynamically
        if (apiDefinition.getHeader() != null) {
            for (Headers header : apiDefinition.getHeader()) {
                httpHeaders.set(header.getKey(), header.getValue());
            }
        }
        // Replace path variables if any
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                url = url.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        // Add query parameters from method argument
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (apiDefinition.getParams() != null && queryParams != null) {
            for (Parameter param : apiDefinition.getParams()) {
                // Always take value from request, ignore default value in DB
                if (queryParams.containsKey(param.getName())) {
                    builder.queryParam(param.getName(), queryParams.get(param.getName()));
                }
            }
        }

        // 4. Build request body
        if (body != null && requestBody != null) {
            // Replace placeholders like {{key}} with actual values from requestBodyMap
            for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                body = body.replace(placeholder, entry.getValue());
            }
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);

        String finalUrl = builder.toUriString();

        try {

            return new RestTemplate().exchange(
                    finalUrl,
                    apiDefinition.getMethod(),
                    entity,
                    Object.class
            );

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error executing API: " + e.getMessage());
        }
    }
}
