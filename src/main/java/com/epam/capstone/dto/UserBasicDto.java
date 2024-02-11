package com.epam.capstone.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UserBasicDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String contactNumber;

    private final LocalDate dateOfBirth;
    private final String username;

    public UserBasicDto(String firstName, String lastName, String contactNumber, LocalDate dateOfBirth, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;

        this.dateOfBirth = dateOfBirth;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }



    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBasicDto entity = (UserBasicDto) o;
        return Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.contactNumber, entity.contactNumber) &&
                Objects.equals(this.dateOfBirth, entity.dateOfBirth) &&
                Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, contactNumber, dateOfBirth, username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "contactNumber = " + contactNumber + ", " +
                "dateOfBirth = " + dateOfBirth + ", " +
                "username = " + username + ")";
    }
}
