package com.epam.capstone.repositories;

import com.epam.capstone.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {


}

