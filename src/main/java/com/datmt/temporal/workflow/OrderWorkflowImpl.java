package com.datmt.temporal.workflow;

import com.datmt.temporal.workflow.activities.OrderActivity;
import com.datmt.temporal.workflow.activities.PaymentActivity;
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

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryoptions)
            .build();
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<>() {{
        put(WorkerHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};

    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, defaultActivityOptions, perActivityMethodOptions);
    private final ShippingActivity shippingActivity = Workflow.newActivityStub(ShippingActivity.class, defaultActivityOptions, perActivityMethodOptions);
    private final PaymentActivity paymentActivity = Workflow.newActivityStub(PaymentActivity.class, defaultActivityOptions, perActivityMethodOptions);

    @Override
    public void processOrder(String customerId, Map<Long, Integer> orderLines, double amount) {
        var orderId = orderActivity.placeOrder(customerId, orderLines);
        paymentActivity.processPayment(orderId, amount);
        shippingActivity.shipOrder(orderId, customerId);
    }
}
