package com.datmt.temporal.workflow.subflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ShippingWorkflow {

    @WorkflowMethod
    void processShipment(Long orderId);
}
