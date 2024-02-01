package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class AuthRequestDto implements Serializable {
    private  String email;
    private  String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRequestDto entity = (AuthRequestDto) o;
        return Objects.equals(this.email, entity.email) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "email = " + email + ", " +
                "passwordHash = " + password + ")";
    }
}
