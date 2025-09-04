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

    @GetMapping("/email/{email}")
    public String findByEmail(@PathVariable("email") String email) {

        return customerService.findUuidByEmail(email);

    }

    @GetMapping("/{uuid}")

    public CustomerResponseDto findCustomerById(@PathVariable("uuid") String uuid) {

        return customerService.findByUuid(uuid);

    }

    @GetMapping("/city/{city}")
    public List<CustomerResponseDto> findByCity(@PathVariable("city") String city) {

        return customerService.findByCity(city);
    }

    @PostMapping
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {

        return customerService.create(customerRequestDto);

    }


    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid) {

        return customerService.delete(uuid);

    }

    @GetMapping("/cin/{cin}")
    public CustomerResponseDto findByCin(@PathVariable("cin") String cin) {

        return customerService.findByCin(cin);

    }

    @GetMapping("/bank/{uuid}")
    public List<CustomerResponseDto> findByBankUuid(@PathVariable("uuid") String uuid) {

        return customerService.findByBankUuid(uuid);

    }

}
