package com.datmt.temporal.shippingservice.workflow;

import com.datmt.temporal.helpers.WorkflowHelper;
import com.datmt.temporal.workflow.subflows.ShippingWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ShippingWorkflowImpl implements ShippingWorkflow {
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<>() {{
        put(WorkflowHelper.ORDER_WORKFLOW_TASK_QUEUE, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final ShippingActivity shippingActivity = Workflow.newActivityStub(ShippingActivity.class, WorkflowHelper.defaultActivityOptions(), perActivityMethodOptions);

    @Override
    public void processShipment(Long orderId) {
        shippingActivity.shipOrder(orderId);
    }
}
