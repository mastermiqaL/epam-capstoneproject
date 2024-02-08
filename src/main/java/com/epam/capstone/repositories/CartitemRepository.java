package com.epam.capstone.repositories;

import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartitemRepository extends JpaRepository<Cartitem, CartitemId> {

    Optional<Cartitem> findById(CartitemId cartitemId);

    List<Cartitem> findByUserId(Integer id);


}
