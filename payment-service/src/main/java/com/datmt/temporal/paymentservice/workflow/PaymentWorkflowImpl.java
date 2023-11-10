package com.datmt.temporal.paymentservice.workflow;

import com.datmt.temporal.helpers.WorkflowHelper;
import com.datmt.temporal.workflow.subflows.PaymentWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PaymentWorkflowImpl implements PaymentWorkflow {


    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<>() {{
        put(WorkflowHelper.ORDER_WORKFLOW_TASK_QUEUE, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final PaymentProcessingActivity orderActivity = Workflow.newActivityStub(PaymentProcessingActivity.class, WorkflowHelper.defaultActivityOptions(), perActivityMethodOptions);
    @Override
    public void processPayment(Long orderId) {
        orderActivity.processPayment(orderId);
    }

}
