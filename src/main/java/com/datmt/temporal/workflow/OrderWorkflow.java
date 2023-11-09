package com.datmt.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;

@WorkflowInterface
public interface OrderWorkflow {
    void processOrder(String orderId, String customerId, String itemId, int quantity);
}
