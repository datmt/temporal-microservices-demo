package com.datmt.temporal.orderservice.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    Long placeOrder(Long customerId, Map<Long, Integer> productLines);

    @ActivityMethod
    void cancelOrder(Long orderId);

    @ActivityMethod
    void updateOrder(Long orderId, String status);

}
