package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class AddressDto implements Serializable {
    private final String country;
    private final String city;
    private final String postalcode;
    private final String street;
    private final String houseNumber;
    private final String additionalNotes;

    public AddressDto(String country, String city, String postalcode, String street, String houseNumber, String additionalNotes) {
        this.country = country;
        this.city = city;
        this.postalcode = postalcode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalNotes = additionalNotes;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto entity = (AddressDto) o;
        return Objects.equals(this.country, entity.country) &&
                Objects.equals(this.city, entity.city) &&
                Objects.equals(this.postalcode, entity.postalcode) &&
                Objects.equals(this.street, entity.street) &&
                Objects.equals(this.houseNumber, entity.houseNumber) &&
                Objects.equals(this.additionalNotes, entity.additionalNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, postalcode, street, houseNumber, additionalNotes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "country = " + country + ", " +
                "city = " + city + ", " +
                "postalcode = " + postalcode + ", " +
                "street = " + street + ", " +
                "houseNumber = " + houseNumber + ", " +
                "additionalNotes = " + additionalNotes + ")";
    }
}
