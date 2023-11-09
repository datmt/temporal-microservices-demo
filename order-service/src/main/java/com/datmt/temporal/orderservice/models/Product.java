package com.datmt.temporal.orderservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "products")
@Data
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;
}
