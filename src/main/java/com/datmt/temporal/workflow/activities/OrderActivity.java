package com.datmt.temporal.workflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    Long placeOrder(String customerId, Map<Long, Integer> productLines);

    @ActivityMethod
    void cancelOrder(Long orderId);

    @ActivityMethod
    void completeOrder(Long orderId);

}
