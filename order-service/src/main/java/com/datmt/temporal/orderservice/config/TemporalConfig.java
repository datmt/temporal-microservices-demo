package com.datmt.temporal.orderservice.config;

import com.datmt.temporal.orderservice.workflow.OrderActivityImpl;
import com.datmt.temporal.workflow.OrderWorkflowImpl;
import com.datmt.temporal.workflow.WorkerHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
        Worker lifecycleWorker = factory.newWorker(WorkerHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE);
        lifecycleWorker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
        lifecycleWorker.registerActivitiesImplementations(orderActivity);

        Worker orderActivityWorker = factory.newWorker(WorkerHelper.WORKFLOW_ORDER_TASK_QUEUE);
        orderActivityWorker.registerActivitiesImplementations(orderActivity);
        factory.start();
    }
}