package com.epam.capstone.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UserDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String contactNumber;
    private final Integer gender;
    private final LocalDate dateOfBirth;
    private final String username;

    public UserDto(String firstName, String lastName, String email, String contactNumber, Integer gender, LocalDate dateOfBirth, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Integer getGender() {
        return gender;
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
        UserDto entity = (UserDto) o;
        return Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.contactNumber, entity.contactNumber) &&
                Objects.equals(this.gender, entity.gender) &&
                Objects.equals(this.dateOfBirth, entity.dateOfBirth) &&
                Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, contactNumber, gender, dateOfBirth, username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "email = " + email + ", " +
                "contactNumber = " + contactNumber + ", " +
                "gender = " + gender + ", " +
                "dateOfBirth = " + dateOfBirth + ", " +
                "username = " + username + ")";
    }
}
