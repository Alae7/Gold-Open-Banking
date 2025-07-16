package com.adaptive.controller;

import com.adaptive.dto.apidefinition.AddressResponseDto;
import com.adaptive.service.apidefinition.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping
    public List<AddressResponseDto> findAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{uuid}")
    public AddressResponseDto findAddressByUuid(@PathVariable("uuid") String uuid) {
        return addressService.findByUuid(uuid);
    }

}
