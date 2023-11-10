package com.datmt.temporal.workflow;

import com.datmt.temporal.workflow.subflows.OrderWorkflow;
import com.datmt.temporal.workflow.subflows.PaymentWorkflow;
import com.datmt.temporal.workflow.subflows.ShippingWorkflow;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OrderLifecycleWorkflowImpl implements OrderLifecycleWorkflow {
    @Override
    public void orchestratingOrder(Long customerId, Map<Long, Integer> orderLines) {
        log.info("Orchestrating order for customer: {}", customerId);
        OrderWorkflow orderWorkflow = Workflow.newChildWorkflowStub(OrderWorkflow.class);
        var orderId = orderWorkflow.createOrder(customerId, orderLines);

        log.info("Order created with id: {}", orderId);
        PaymentWorkflow paymentWorkflow = Workflow.newChildWorkflowStub(PaymentWorkflow.class);
        paymentWorkflow.processPayment(orderId);

        log.info("Payment processed for order: {}", orderId);

        ShippingWorkflow shippingWorkflow = Workflow.newChildWorkflowStub(ShippingWorkflow.class);
        shippingWorkflow.processShipment(orderId);

        log.info("Shipment processed for order: {}", orderId);

        log.info("Order completed with id: {}", orderId);
    }
}
