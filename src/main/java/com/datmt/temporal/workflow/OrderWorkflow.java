package com.datmt.temporal.workflow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.Map;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    void processOrder(String customerId, Map<Long, Integer> orderLines, double amount);
}
