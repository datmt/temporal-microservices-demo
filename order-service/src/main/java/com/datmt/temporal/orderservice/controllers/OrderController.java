package com.datmt.temporal.orderservice.controllers;

import com.datmt.temporal.workflow.OrderWorkflow;
import com.datmt.temporal.workflow.WorkerHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("orders")
public class OrderController {


    //create order here
    @PostMapping
    public void createOrder(@RequestBody CreateOrderRequest request) {


        double amount = 100 * request.items().size();

        var workflowClient = WorkerHelper.getWorkflowClient();

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(WorkerHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE)
                .build();
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, options);

        // Asynchronously start the workflow execution
        WorkflowClient.start(workflow::processOrder, request.customerId(), request.items(), amount);

    }
}


