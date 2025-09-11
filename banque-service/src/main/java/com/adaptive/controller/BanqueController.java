package com.adaptive.controller;

import com.adaptive.dto.BanqueRequestDto;
import com.adaptive.dto.BanqueResponseDto;
import com.adaptive.entity.ApiDefinition;
import com.adaptive.entity.Banque;
import com.adaptive.repository.ApiDefinitionRepository;
import com.adaptive.repository.BanqueRepository;
import com.adaptive.service.BanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banque")
public class BanqueController {

    @Autowired
    private BanqueService banqueService;


    @GetMapping("/all")
    public List<BanqueResponseDto> findAll() {

        return banqueService.findAll();

    }

    @GetMapping("/{uuid}")
    public BanqueResponseDto findByUuid(@PathVariable("uuid") String uuid) {

        return banqueService.findByUuid(uuid);

    }

    @PostMapping
    public BanqueResponseDto create(@RequestBody BanqueRequestDto banqueRequestDto) {

        return banqueService.create(banqueRequestDto);

    }

    @PutMapping("/{uuid}")
    public BanqueResponseDto update(@RequestBody BanqueRequestDto banqueRequestDto, @PathVariable("uuid") String uuid) {

        return banqueService.update(banqueRequestDto,uuid);

    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid) {

        return banqueService.delete(uuid);

    }

    @GetMapping("/code/{uuid}")
    public BanqueResponseDto findCodeByUuid(@PathVariable("uuid") String uuid) {

        return banqueService.findCodeByUuid(uuid);

    }

}
