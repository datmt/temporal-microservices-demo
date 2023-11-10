package com.datmt.temporal.orderservice.workflow;

import com.datmt.temporal.helpers.WorkflowHelper;
import com.datmt.temporal.workflow.subflows.OrderWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OrderWorkflowImpl implements OrderWorkflow {
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<>() {{
        put(WorkflowHelper.ORDER_WORKFLOW_TASK_QUEUE, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, WorkflowHelper.defaultActivityOptions(), perActivityMethodOptions);

    @Override
    public Long createOrder(Long customerId, Map<Long, Integer> orderLines) {
        return orderActivity.placeOrder(customerId, orderLines);
    }

}
