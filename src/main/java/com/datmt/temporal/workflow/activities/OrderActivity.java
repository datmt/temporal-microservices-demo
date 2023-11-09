package com.datmt.temporal.workflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    void placeOrder(String orderId, String customerId, String itemId, int quantity);

    @ActivityMethod
    void cancelOrder(String orderId);

}
