package com.datmt.temporal.workflow.subflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.Map;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    Long createOrder(Long customerId, Map<Long, Integer> orderLines);
}
