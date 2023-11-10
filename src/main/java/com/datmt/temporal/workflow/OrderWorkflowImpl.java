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
            .setMaximumInterval(Duration.ofSeconds(10))//set to 10 seconds for demo, default is 100 seconds
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryoptions)
            .build();

    private final Map<String, ActivityOptions> methodOptions = new HashMap<>() {{
        //Not sure what to put in the key here, but it seems to work with the value. Use at your own risk.
        put(WorkerHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, defaultActivityOptions, methodOptions);
    private final ShippingActivity shippingActivity = Workflow.newActivityStub(ShippingActivity.class, defaultActivityOptions, methodOptions);
    private final PaymentActivity paymentActivity = Workflow.newActivityStub(PaymentActivity.class, defaultActivityOptions, methodOptions);


    @Override
    public void processOrder(String customerId, Map<Long, Integer> orderLines, double amount) {
        log.info("WORKFLOW: Processing order for customer {}", customerId);
        var orderId = orderActivity.placeOrder(customerId, orderLines);
        log.info("WORKFLOW: Order {} placed for customer {}", orderId, customerId);
        paymentActivity.processPayment(orderId, amount);
        log.info("WORKFLOW: Payment processed for order {} of customer {}", orderId, customerId);
        shippingActivity.processShipment(orderId);
        log.info("WORKFLOW: Order {} shipped for customer {}", orderId, customerId);

        orderActivity.completeOrder(orderId);
        log.info("WORKFLOW: Order {} completed for customer {}", orderId, customerId);
    }

}
