package com.datmt.temporal.shippingservice.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ShippingActivity {
    @ActivityMethod
    void shipOrder(Long orderId);
}
