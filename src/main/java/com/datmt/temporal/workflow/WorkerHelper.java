package com.datmt.temporal.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import java.time.Duration;
import java.util.Optional;

public class WorkerHelper {
    public static final String WORKFLOW_ORDER_TASK_QUEUE = "WorkflowOrderTaskQueue";
    public static final String WORKFLOW_PAYMENT_TASK_QUEUE = "WorkflowPaymentTaskQueue";
    public static final String WORKFLOW_SHIPPING_TASK_QUEUE = "WorkflowShippingTaskQueue";
    public static final String ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE = "OrderLifecycleWorkflowTaskQueue";


    private static WorkflowOptions getWorkflowOptions(String taskQueue, String workflowId) {
        var builder = WorkflowOptions.newBuilder();

        builder.setWorkflowId(workflowId);
        builder.setTaskQueue(taskQueue);
        builder.setWorkflowRunTimeout(java.time.Duration.ofMinutes(5));
        builder.setWorkflowTaskTimeout(java.time.Duration.ofMinutes(1));
        return builder.build();
        // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
    }

    public static WorkflowClient getWorkflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("192.168.100.5:7233")
                .build());
        return WorkflowClient.newInstance(service);
    }


    public static RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();


    public static ActivityOptions defaultActivityOptions() {
        return
                ActivityOptions.newBuilder()
                        // Timeout options specify when to automatically timeout Activities if the process is taking too long.
                        .setStartToCloseTimeout(Duration.ofSeconds(5))
                        .setRetryOptions(retryoptions)
                        .build();
    }

}
