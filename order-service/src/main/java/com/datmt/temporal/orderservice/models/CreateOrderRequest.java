package com.datmt.temporal.orderservice.models;

import java.util.Map;

public record CreateOrderRequest(
        Long customerId,
        Map<Long, Integer> items
) {
}
