package com.epam.capstone.repositories;

import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartitemRepository extends JpaRepository<Cartitem,CartitemId> {
    @Override
    Optional<Cartitem> findById(CartitemId cartitemId);

    @Override
    boolean existsById(CartitemId cartitemId);


    List<Cartitem> findByCart_User_Id(Integer id);
    //todo:insertebi da delete operaciebi
}
