package com.adaptive.controller;

import com.adaptive.dto.AnomalyResponseDto;
import com.adaptive.dto.TransactionRequestDto;
import com.adaptive.dto.TransactionResponseDto;
import com.adaptive.entity.TypeTransaction;
import com.adaptive.service.TransactionService;
import com.adaptive.service.anomaly.AnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AnomalyService anomalyService;


    @GetMapping()
    public List<TransactionResponseDto> findAll(){

        return transactionService.findAll();

    }

    @GetMapping("type/{type}")
    public List<TransactionResponseDto> findByType(@PathVariable("type") TypeTransaction type) {

        return transactionService.findByType(type);

    }

    @GetMapping("compte/source/{rib}")
    public List<TransactionResponseDto> findBySourceRib(@PathVariable("rib") Long rib) {

        return transactionService.findAllBySourceRib(rib);

    }

    @GetMapping("compte/target/{rib}")
    public List<TransactionResponseDto> findByTargetRib(@PathVariable("rib") Long rib) {

        return transactionService.findAllByTargetRib(rib);

    }

    @GetMapping("/{uuid}")
    public TransactionResponseDto findByUuid(@PathVariable("uuid") String uuid) {

        return transactionService.findByUuid(uuid);

    }


    @PostMapping
    public TransactionResponseDto createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {

        return transactionService.create(transactionRequestDto);

    }


    @GetMapping("/anomaly/{id}")
    public AnomalyResponseDto findAnomalyById(@PathVariable("id") Long id) {

        return anomalyService.findByid(id);

    }

    @GetMapping("/anomaly")
    public List<AnomalyResponseDto> findAllAnomaly() {

        return anomalyService.findAll();

    }

    @GetMapping("/anomaly/{traite}")
    public List<AnomalyResponseDto> findAllAnomalyTraite(@PathVariable("traite") boolean traite) {

        return anomalyService.findAllIsTraite(traite);

    }


}
