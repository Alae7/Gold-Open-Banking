package com.adaptive.service;

import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.Customer;
import com.adaptive.mapper.CustomerMapper;
import com.adaptive.repository.CustomerRepository;
import com.adaptive.utils.CloudinaryService;
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


        Customer customer = customerMapper.toEntity(customerRequestDto);
        customerRepository.save(customer);


        return customerMapper.toResponseDto(customer);

    }

    @Override
    public String delete(String uuid) {

        Customer customer = customerRepository.findByUuid(uuid);
        customer.setDeleted(true);
        return " customer has been deleted ";
    }

    @Override
    public List<CustomerResponseDto> findAllByDeletedIsFalse() {
        return customerMapper.toResponseDtoList(customerRepository.findAllByDeletedIsFalse());
    }

    @Override
    public String findUuidByEmail(String email) {

        return customerRepository.findByEmail(email).getUuid();

    }

    @Override
    public List<CustomerResponseDto> findByCity(String city) {
        return customerMapper.toResponseDtoList(customerRepository.findByCity(city));
    }
}
