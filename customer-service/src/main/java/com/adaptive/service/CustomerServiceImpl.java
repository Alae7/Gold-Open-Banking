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
    public String create(CustomerRequestDto customerRequestDto) {
        Customer customer = customerMapper.toEntity(customerRequestDto);
        System.out.println("1. Created customer entity: " + customer.getUuid());

        String code = banqueFeinClient.findCodeByUuid(customerRequestDto.getBanqueUuid()).getCode();
        System.out.println("2. Got banque code: " + code);

        CompteRequestDto compteRequestDto = Utils.createCompteRequest(customerRequestDto.getBanqueUuid(),customer.getUuid(),customerRequestDto.getTypeCompte());
        boolean status = compteFeinClient.createCompte(compteRequestDto);
        System.out.println("3. Compte creation status: " + status);

        if(status){
            System.out.println("4. Status is true, saving customer...");
            customer = customerRepository.save(customer);
            System.out.println("5. Customer saved with ID: " + customer.getId()); // Assuming you have an ID field

            CustomerResponseDto customerResponseDto = customerMapper.toResponseDto(customer);
            ExecuteRequest executeRequest = Utils.createExecuteRequest(customerResponseDto, code);
            try {
                banqueFeinClient.execute(executeRequest);
                System.out.println("6. Execute request successful");
            } catch (Exception e) {
                System.out.println("6. Execute request failed: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("4. Status is FALSE - customer NOT saved!");
        }

        System.out.println("7. Returning UUID: " + customer.getUuid());
        return customer.getUuid();
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
