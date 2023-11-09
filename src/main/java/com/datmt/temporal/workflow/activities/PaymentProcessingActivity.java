package com.datmt.temporal.workflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentProcessingActivity {

    @ActivityMethod
    void processPayment(String orderId, int amount);

    @ActivityMethod
    void cancelPayment(String orderId);

}
