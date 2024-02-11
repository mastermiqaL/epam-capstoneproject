package com.epam.capstone.services;

import com.epam.capstone.dto.AddressDto;
import com.epam.capstone.dto.AddressDtoMapper;
import com.epam.capstone.dto.AddressPlacingDto;
import com.epam.capstone.entities.Address;
import com.epam.capstone.repositories.AddressRepository;
import com.epam.capstone.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    private final AddressDtoMapper addressDtoMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressDtoMapper addressDtoMapper) {
        this.addressRepository = addressRepository;
        this.addressDtoMapper = addressDtoMapper;
    }

    @Override
    public AddressDto getAddressByUserId(Integer id) {
        Address address=addressRepository.findByUser(id).get();
        return addressDtoMapper.apply(address);
    }

    @Override
    public void saveAddress(AddressPlacingDto addressPlacingDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return;
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Address address = new Address();
        address.setUser(userDetails.getUserId());
        address.setCountry(addressPlacingDto.getCountry());
        address.setCity(addressPlacingDto.getCity());
        address.setPostalcode(addressPlacingDto.getPostalcode());
        address.setStreet(addressPlacingDto.getStreet());
        address.setHouseNumber(addressPlacingDto.getHouseNumber());
        address.setAdditionalNotes(addressPlacingDto.getAdditionalNotes());
         addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }
}
