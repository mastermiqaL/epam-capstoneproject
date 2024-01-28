package com.epam.capstone.services;

import com.epam.capstone.dto.AddressDto;
import com.epam.capstone.entities.Address;

public interface AddressService {
    AddressDto getAddressByUserId(Integer id);
    Address saveAddress(Address address);
    void deleteAddress(Integer id);
}
