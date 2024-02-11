package com.epam.capstone.services;

import com.epam.capstone.entities.Order;
import com.epam.capstone.entities.Payment;
import com.epam.capstone.repositories.PaymentRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void save(Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getAmount());
        payment.setPaymentDate(LocalDate.now());
        paymentRepository.save(payment);
    }
}
