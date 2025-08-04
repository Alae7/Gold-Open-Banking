package com.adaptive.controller;

import com.adaptive.dto.CreditRequestDto;
import com.adaptive.dto.CreditResponseDto;
import com.adaptive.entity.CreditStatus;
import com.adaptive.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping("/{uuid}")
    public CreditResponseDto findByCreditUuid(@PathVariable("uuid") String uuid) {

        return creditService.findByUuid(uuid);

    }

    @PostMapping
    public CreditResponseDto createCredit(@RequestBody CreditRequestDto creditRequestDto) {

        return creditService.create(creditRequestDto);

    }

    @GetMapping("/compte/{rib}")
    public List<CreditResponseDto> findCreditByRib(@PathVariable("rib") Long rib) {

        return creditService.findByCompteRib(rib);

    }

    @PutMapping("/{uuid}")
    public void changeStatusCredit(@PathVariable("uuid") String uuid, @RequestBody CreditStatus creditStatus) {

        creditService.changeStatus(uuid, creditStatus);

    }

}
