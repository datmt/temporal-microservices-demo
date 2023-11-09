package com.datmt.temporal.orderservice.repositories;

import com.datmt.temporal.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
