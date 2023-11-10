package com.datmt.temporal.orderservice;

import com.datmt.temporal.helpers.WorkflowHelper;
import com.datmt.temporal.orderservice.models.CreateOrderRequest;
import com.datmt.temporal.orderservice.repositories.ProductRepository;
import com.datmt.temporal.workflow.OrderLifecycleWorkflow;
import com.datmt.temporal.workflow.subflows.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final ProductRepository productRepository;

    //create order here
    @PostMapping
    public void createOrder(@RequestBody CreateOrderRequest request) {

        var amount = request.items().entrySet().stream()
                .mapToDouble(entry -> {
                    var product = productRepository.findById(entry.getKey());
                    return product.map(value -> value.getPrice() * entry.getValue()).orElse(0.0);
                })
                .sum();
        log.info("Total amount: {}", amount);

        var workflowClient = WorkflowHelper.getWorkflowClient();

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(WorkflowHelper.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE)
                // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
                .build();
        OrderLifecycleWorkflow workflow = workflowClient.newWorkflowStub(OrderLifecycleWorkflow.class, options);

        // Asynchronously start the workflow execution
        WorkflowClient.start(workflow::orchestratingOrder, request.customerId(), request.items());

    }
}
