package com.datmt.temporal.paymentservice.config;

import com.datmt.temporal.workflow.OrderWorkflowImpl;
import com.datmt.temporal.workflow.WorkerHelper;
import com.datmt.temporal.workflow.activities.PaymentActivity;
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
    private PaymentActivity paymentActivity;


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
        lifecycleWorker.registerActivitiesImplementations(paymentActivity);


        Worker worker = factory.newWorker(WorkerHelper.WORKFLOW_PAYMENT_TASK_QUEUE);
        worker.registerActivitiesImplementations(paymentActivity);
        factory.start();
    }
}