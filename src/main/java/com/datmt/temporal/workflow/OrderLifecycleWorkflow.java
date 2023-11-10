package com.datmt.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.Map;

@WorkflowInterface
public interface OrderLifecycleWorkflow {

    @WorkflowMethod
    void orchestratingOrder(Long customerId, Map<Long, Integer> orderLines);
}
