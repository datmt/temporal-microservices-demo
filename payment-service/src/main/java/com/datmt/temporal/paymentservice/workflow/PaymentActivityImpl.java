package com.datmt.temporal.paymentservice.workflow;

import com.datmt.temporal.workflow.activities.PaymentActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentActivityImpl implements PaymentActivity {
    @Override
    public void processPayment(Long orderId, double amount) {
        log.info("Payment processed for order id: {} and amount: {}", orderId, amount);
    }

    @Override
    public void cancelPayment(Long orderId) {
        log.info("Payment cancelled for order id: {}", orderId);
        throw new RuntimeException("Payment cancelled");
    }
}
