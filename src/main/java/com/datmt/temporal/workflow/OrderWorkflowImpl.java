package com.datmt.temporal.workflow;

import com.datmt.temporal.workflow.activities.OrderActivity;
import com.datmt.temporal.workflow.activities.PaymentProcessingActivity;
import com.datmt.temporal.workflow.activities.ShippingActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class OrderWorkflowImpl implements OrderWorkflow {

    private static final String STORE_ORDER = "STORE_ORDER";
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<>() {{
        put(STORE_ORDER, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};

    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class);
    private final ShippingActivity shippingActivity = Workflow.newActivityStub(ShippingActivity.class);
    private final PaymentProcessingActivity paymentActivity = Workflow.newActivityStub(PaymentProcessingActivity.class, defaultActivityOptions, perActivityMethodOptions);

    @Override
    public void processOrder(String customerId, Map<Long, Integer> orderLines, double amount) {
        var orderId = orderActivity.placeOrder(customerId, orderLines);
        log.info("Order placed with id {}", orderId);
        paymentActivity.processPayment(orderId, amount);
        shippingActivity.shipOrder(orderId, customerId);
    }
}
