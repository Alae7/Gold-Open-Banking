package com.adaptive.controller;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/all")
    public List<CustomerResponseDto> findAllCustomers() {

        return customerService.findAll();

    }

    @GetMapping
    public List<CustomerResponseDto> findNotDeleted() {

        return customerService.findByNotDeleted();

    }

    @GetMapping("/email")
    public String findByEmail(@RequestParam String email) {

        return customerService.findUuidByEmail(email);

    }

    @GetMapping("/{uuid}")
    public CustomerResponseDto findCustomerById(@PathVariable("uuid") String uuid) {

        return customerService.findByUuid(uuid);

    }

//    @GetMapping("/city")
//    public List<CustomerResponseDto> findByCity(@RequestParam("city") String city) {
//
//        return customerService.findByCity(city);
//
//    }

    @PostMapping
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {

        return customerService.create(customerRequestDto);

    }

    @PutMapping("/{uuid}")
    public CustomerResponseDto update(@RequestBody CustomerRequestDto customerRequestDto, @PathVariable("uuid") String uuid) {

        return customerService.update(customerRequestDto,uuid);

    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid) {

        return customerService.delete(uuid);

    }

}
