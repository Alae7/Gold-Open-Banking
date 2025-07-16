package com.adaptive.controller;

import com.adaptive.dto.apidefinition.ApiDefinitionRequestDto;
import com.adaptive.dto.apidefinition.ApiDefinitionResponseDto;
import com.adaptive.service.apidefinition.ApiDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiDefinitionResponseDto create(@RequestBody ApiDefinitionRequestDto apiDefinitionRequestDto) {
        return apiDefinitionService.create(apiDefinitionRequestDto);
    }

    @PutMapping("/{uuid}")
    public void update(@PathVariable("uuid") String uuid, @RequestBody ApiDefinitionRequestDto apiDefinitionRequestDto) {
        apiDefinitionService.update(uuid,apiDefinitionRequestDto);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") String uuid) {
        apiDefinitionService.delete(uuid);
    }

}
