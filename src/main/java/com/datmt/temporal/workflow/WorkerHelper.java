package com.datmt.temporal.workflow;

import io.temporal.activity.Activity;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class WorkerHelper {
    public static final String ORDER_WORKFLOW_TASK_QUEUE = "OrderWorkflowTaskQueue";

    public static <T> void registerWorker(T t) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("192.168.100.5:7233")
                .build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        // Worker factory is used to create Workers that poll specific Task Queues.
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(ORDER_WORKFLOW_TASK_QUEUE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(t);
        // Start listening to the Task Queue.
        factory.start();
    }
}
