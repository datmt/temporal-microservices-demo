package com.datmt.temporal.orderservice.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderLine {
    private int quantity;
    private Long productId;
}
