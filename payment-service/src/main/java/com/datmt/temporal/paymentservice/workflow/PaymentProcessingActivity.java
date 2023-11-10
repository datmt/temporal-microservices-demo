package com.datmt.temporal.paymentservice.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentProcessingActivity {
    @ActivityMethod
    void processPayment(Long orderId);
}
