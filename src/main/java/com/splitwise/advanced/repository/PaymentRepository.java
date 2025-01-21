package com.splitwise.advanced.repository;

import com.splitwise.advanced.entities.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
