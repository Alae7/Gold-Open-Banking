package com.adaptive.service.apidefinition;

import com.adaptive.dto.apidefinition.AddressResponseDto;
import com.adaptive.dto.CustomerRequestDto;
import com.adaptive.entity.Address;
import com.adaptive.mapper.AddressMapper;
import com.adaptive.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressResponseDto findByUuid(String uuid) {

        return addressMapper.toResponseDto(addressRepository.findByUuid(uuid));

    }

    @Override
    public List<AddressResponseDto> findAll() {

        return addressMapper.toResponseDtoList(addressRepository.findAll());

    }

    @Override
    public Address create(CustomerRequestDto CustomerRequestDto) {
        Address address = addressMapper.toEntity(CustomerRequestDto);
        addressRepository.save(address);
        return address;
    }

    @Override
    public void update(CustomerRequestDto customerRequestDto, String uuid) {
        Address address = addressRepository.findByUuid(uuid);
        address.setCity(customerRequestDto.getCity());
        address.setPostalCode(customerRequestDto.getPostalCode());
        address.setHouseNumber(customerRequestDto.getHouseNumber());
        address.setStreet(customerRequestDto.getStreet());
        addressRepository.save(address);

    }

    @Override
    public void delete(String uuid) {

        Address address = addressRepository.findByUuid(uuid);
        address.setDeleted(true);
        addressRepository.save(address);

    }
}
