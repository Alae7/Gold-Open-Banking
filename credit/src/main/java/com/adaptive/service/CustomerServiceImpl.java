package com.adaptive.service;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.Address;
import com.adaptive.entity.Customer;
import com.adaptive.mapper.CustomerMapper;
import com.adaptive.repository.AddressRepository;
import com.adaptive.repository.CustomerRepository;
import com.adaptive.service.apidefinition.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public CustomerResponseDto findByUuid(String uuid) {
        return customerMapper.toResponseDto(customerRepository.findByUuid(uuid));
    }

    @Override
    public List<CustomerResponseDto> findAll() {
        return customerMapper.toResponseDtoList(customerRepository.findAll());
    }

    @Override
    public CustomerResponseDto create(CustomerRequestDto customerRequestDto) {

        Address address = addressService.create(customerRequestDto);
        Customer customer = customerMapper.toEntity(customerRequestDto);
        customer.setAddress(address);
        customerRepository.save(customer);
        return customerMapper.toResponseDto(customer);

    }

    @Override
    public CustomerResponseDto update(CustomerRequestDto customerRequestDto, String uuid) {

        Customer customer = customerRepository.findByUuid(uuid);
        addressService.update(customerRequestDto, customer.getAddress().getUuid());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
        customer.setPhone(customerRequestDto.getPhone());
        customerRepository.save(customer);
        return customerMapper.toResponseDto(customer);

    }

    @Override
    public String delete(String uuid) {

        Customer customer = customerRepository.findByUuid(uuid);
        addressService.delete(customer.getAddress().getUuid());
        customer.setDeleted(true);
        return " customer has been deleted ";
    }

    @Override
    public List<CustomerResponseDto> findByNotDeleted() {
        return customerMapper.toResponseDtoList(customerRepository.findAllByIsDeletedFalse());
    }

    @Override
    public String findUuidByEmail(String email) {

        return customerRepository.findByEmail(email).getUuid();

    }

    @Override
    public List<CustomerResponseDto> findByCity(String city) {
        return customerMapper.toResponseDtoList(customerRepository.findByAddress_City(city));
    }
}
