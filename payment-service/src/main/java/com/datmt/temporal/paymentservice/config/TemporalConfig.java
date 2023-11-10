package com.datmt.temporal.paymentservice.config;

import com.datmt.temporal.paymentservice.workflow.PaymentProcessingActivityImpl;
import com.datmt.temporal.paymentservice.workflow.PaymentWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static com.datmt.temporal.helpers.WorkflowHelper.PAYMENT_WORKFLOW_TASK_QUEUE;

@Configuration
public class TemporalConfig {

    @Autowired
    private PaymentProcessingActivityImpl paymentActivity;


    @PostConstruct
    public void startWorkers() {
        var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("192.168.100.5:7233")
                .build());
        var client = WorkflowClient.newInstance(stub);

        var factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(PAYMENT_WORKFLOW_TASK_QUEUE);

        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(PaymentWorkflowImpl.class);
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(paymentActivity);
        factory.start();
    }
}
