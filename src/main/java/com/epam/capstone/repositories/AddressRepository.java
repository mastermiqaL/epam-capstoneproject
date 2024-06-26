package com.epam.capstone.repositories;

import com.epam.capstone.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    Optional<Address> findByUser(Integer id);


}
