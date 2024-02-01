package com.epam.capstone.repositories;

import com.epam.capstone.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUserId(Integer id);

    //todo:insert (delete?)

}
