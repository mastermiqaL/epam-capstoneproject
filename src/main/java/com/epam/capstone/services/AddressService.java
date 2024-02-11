package com.epam.capstone.services;

import com.epam.capstone.dto.AddressDto;
import com.epam.capstone.dto.AddressPlacingDto;

public interface AddressService {
    AddressDto getAddressByUserId(Integer id);
    void saveAddress(AddressPlacingDto address);
    void deleteAddress(Integer id);
}
