package com.adaptive.controller;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.service.apidefinition.ApiDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apiDefinition")
public class ApiDefinitionController {

    @Autowired
    private ApiDefinitionService apiDefinitionService;


    @GetMapping("/banque/{banqueUuid}")
    public List<ApiDefinitionResponseDto> findByBanqueUuid(@PathVariable("banqueUuid") String banqueUuid) {
        return apiDefinitionService.findByBanqueUuid(banqueUuid);
    }

    @GetMapping("/{uuid}")
    public ApiDefinitionResponseDto findByUuid(@PathVariable("uuid") String uuid) {
        return apiDefinitionService.findByUuid(uuid);
    }


    @PostMapping
    public String create(@RequestBody List<ApiDefinitionRequestDto> apiDefinitionRequestDto) {
        return apiDefinitionService.create(apiDefinitionRequestDto);
    }

    @PutMapping("/{uuid}")
    public void update(@PathVariable("uuid") String uuid, @RequestBody String url) {
        apiDefinitionService.update(uuid,url);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") String uuid) {
        apiDefinitionService.delete(uuid);
    }

    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody ExecuteRequest executeRequest) {

        return apiDefinitionService.executeApi(executeRequest);

    }

    @GetMapping("/execute")
    public List<Object> execute() {

        return apiDefinitionService.getFromAllApi();

    }

}
