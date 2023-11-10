package com.datmt.temporal.shippingservice.repositories;

import com.datmt.temporal.shippingservice.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
