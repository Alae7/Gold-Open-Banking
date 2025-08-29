package com.adaptive.service;

import com.adaptive.config.ExecuteRequest;
import com.adaptive.dto.CompteRequestDto;
import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.dto.CustomerResponseDto;
import com.adaptive.entity.Customer;
import com.adaptive.mapper.CustomerMapper;
import com.adaptive.openFeinController.BanqueFeinClient;
import com.adaptive.openFeinController.CompteFeinClient;
import com.adaptive.repository.CustomerRepository;
import com.adaptive.utils.CloudinaryService;
import com.adaptive.utils.Utils;
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
    private BanqueFeinClient banqueFeinClient;

    @Autowired
    private CompteFeinClient compteFeinClient;


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

        ExecuteRequest executeRequest = new ExecuteRequest();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        Customer customer = customerMapper.toEntity(customerRequestDto);
        String code = banqueFeinClient.findCodeByUuid(customerRequestDto.getBanqueUuid());
        CompteRequestDto compteRequestDto = Utils.createCompteRequest(customerRequestDto.getBanqueUuid(),customer.getUuid(),customerRequestDto.getTypeCompte());
        boolean status = compteFeinClient.createCompte(compteRequestDto);

        if(status){
            customerRepository.save(customer);
            customerResponseDto = customerMapper.toResponseDto(customer);
            executeRequest = Utils.createExecuteRequest(customerResponseDto, code);
        }

        customerResponseDto.setStat(status);

        try {
            banqueFeinClient.execute(executeRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customerResponseDto;


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

    @Override
    public CustomerResponseDto findByCin(String cin){
        return customerMapper.toResponseDto(customerRepository.findByCin(cin));
    }

    @Override
    public List<CustomerResponseDto> findByBankUuid(String bankUuid) {
        return customerMapper.toResponseDtoList(customerRepository.findByBanqueUuid(bankUuid));
    }
}
