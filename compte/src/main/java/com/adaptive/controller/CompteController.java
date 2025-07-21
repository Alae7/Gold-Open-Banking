package com.adaptive.controller;

import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CompteResponseDto;
import com.adaptive.model.Transaction;
import com.adaptive.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compte")
public class CompteController {

    @Autowired
    private CompteService compteService;


    @GetMapping("/all/{banqueUuid}")
    public List<CompteResponseDto> findByBanqueUuid(@PathVariable("banqueUuid") String banqueUuid) {

        return compteService.findByBanqueUuid(banqueUuid);

    }

    @GetMapping("/{rib}")
    public CompteResponseDto findByRib(@PathVariable("rib") Long rib) {

        return compteService.findByRib(rib);

    }

    @GetMapping("/customer/{rib}")
    public String findCustomerUuidByRib(@PathVariable("rib") Long rib) {

        return compteService.findCustomerUuidByRib(rib);

    }


    @PostMapping
    public CompteResponseDto createCompte(@RequestBody CompteRequestDto compteRequestDto) {

        return compteService.create(compteRequestDto);

    }


    @DeleteMapping("/{rib}")
    public String delete(@PathVariable("rib") Long rib) {

        return compteService.delete(rib);

    }

    @PutMapping("/{rib}")
    public String versement(@PathVariable("rib") Long rib, @RequestBody Transaction transaction) {
        return compteService.update(rib, transaction);
    }

}
