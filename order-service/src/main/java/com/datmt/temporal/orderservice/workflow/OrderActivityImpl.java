package com.datmt.temporal.orderservice.workflow;

import com.datmt.temporal.orderservice.models.Order;
import com.datmt.temporal.orderservice.models.OrderLine;
import com.datmt.temporal.orderservice.repositories.OrderRepository;
import com.datmt.temporal.workflow.activities.OrderActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderActivityImpl implements OrderActivity {
    private OrderRepository orderRepository;

    @Autowired
    public void setMessageService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long placeOrder(String customerId, Map<Long, Integer> productLines) {
//
//        var order = new Order();
//        order.setCustomerId(customerId);
//        order.setLines(productLines.entrySet().stream()
//                .map(entry -> {
//                    var line = new OrderLine();
//                    line.setProductId(entry.getKey());
//                    line.setQuantity(entry.getValue());
//                    return line;
//                })
//                .toList());
//
//        var savedOrder = orderRepository.save(order);
//
//        return savedOrder.getId();

        return 1L;
    }

    @Override
    public void cancelOrder(Long orderId) {
        updateOrder(orderId, "CANCELLED");
    }

    @Override
    public void updateOrder(Long orderId, String status) {
        var oder = orderRepository.findById(orderId);
        if (oder.isPresent()) {
            var order = oder.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}
