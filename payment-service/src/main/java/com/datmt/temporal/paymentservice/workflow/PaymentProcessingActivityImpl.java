package com.datmt.temporal.paymentservice.workflow;

import com.datmt.temporal.paymentservice.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessingActivityImpl implements PaymentProcessingActivity {

    private PaymentRepository paymentRepository;

    @Autowired
    private void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void processPayment(Long orderId) {
        log.info("Processing payment for order: {}", orderId);
    }
}
