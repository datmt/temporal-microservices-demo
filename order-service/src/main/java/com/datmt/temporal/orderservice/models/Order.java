package com.datmt.temporal.orderservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;

    @ElementCollection
    private List<OrderLine> lines;

    private String status;
}
