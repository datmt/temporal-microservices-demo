package com.datmt.temporal.orderservice.config;

import com.datmt.temporal.orderservice.workflow.OrderActivityImpl;
import com.datmt.temporal.orderservice.workflow.OrderWorkflowImpl;
import com.datmt.temporal.workflow.OrderLifecycleWorkflow;
import com.datmt.temporal.workflow.OrderLifecycleWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static com.datmt.temporal.helpers.WorkflowHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE;
import static com.datmt.temporal.helpers.WorkflowHelper.ORDER_WORKFLOW_TASK_QUEUE;

@Configuration
public class TemporalConfig {

    @Autowired
    private OrderActivityImpl orderActivity;


    @PostConstruct
    public void startWorkers() {
        var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("192.168.100.5:7233")
                .build());
        var client = WorkflowClient.newInstance(stub);

        var factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(ORDER_WORKFLOW_TASK_QUEUE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);

        Worker lifecycleWorker = factory.newWorker(ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE);
        lifecycleWorker.registerWorkflowImplementationTypes(OrderLifecycleWorkflowImpl.class);
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(orderActivity);
        factory.start();
    }
}
