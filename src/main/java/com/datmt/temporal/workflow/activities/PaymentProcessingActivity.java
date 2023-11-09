package com.datmt.temporal.workflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentProcessingActivity {

    @ActivityMethod
    void processPayment(Long orderId, double amount);

    @ActivityMethod
    void cancelPayment(Long orderId);

}
