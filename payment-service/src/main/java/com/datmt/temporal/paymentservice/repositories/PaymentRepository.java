package com.datmt.temporal.paymentservice.repositories;

import com.datmt.temporal.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
