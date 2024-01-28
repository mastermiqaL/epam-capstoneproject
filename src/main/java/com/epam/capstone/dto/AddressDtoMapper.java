package com.epam.capstone.dto;

import com.epam.capstone.entities.Address;

import java.util.function.Function;

public class AddressDtoMapper implements Function<Address,AddressDto> {
    @Override
    public AddressDto apply(Address address) {
        return new AddressDto(
                address.getCountry(),
                address.getCity(),
                address.getPostalcode(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getAdditionalNotes()
        );
    }
}
