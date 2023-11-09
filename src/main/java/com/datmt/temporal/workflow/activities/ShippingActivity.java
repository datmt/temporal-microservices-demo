package com.datmt.temporal.workflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ShippingActivity {

    @ActivityMethod
    void shipOrder(String orderId, String customerId);

    @ActivityMethod
    void cancelShipping(String orderId);
}
