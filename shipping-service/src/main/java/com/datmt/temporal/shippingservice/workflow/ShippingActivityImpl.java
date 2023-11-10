package com.datmt.temporal.shippingservice.workflow;

import com.datmt.temporal.workflow.activities.ShippingActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShippingActivityImpl implements ShippingActivity {
    @Override
    public void shipOrder(Long orderId, String customerId) {
        log.info("Order shipped for order id: {} and customer id: {}", orderId, customerId);
    }

    @Override
    public void cancelShipping(String orderId) {
        log.info("Shipping cancelled for order id: {}", orderId);
        throw new RuntimeException("Shipping cancelled");
    }
}
