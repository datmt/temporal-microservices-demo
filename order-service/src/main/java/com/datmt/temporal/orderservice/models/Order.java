package com.datmt.temporal.orderservice.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity(name = "orders")
@Data
public class Order {
    @Id
    private Long id;
    private String customerId;

    @ElementCollection
    private List<OrderLine> lines;
}
