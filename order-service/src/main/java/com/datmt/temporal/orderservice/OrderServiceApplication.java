package com.datmt.temporal.orderservice;

import com.datmt.temporal.orderservice.workflow.OrderActivityImpl;
import com.datmt.temporal.workflow.WorkerHelper;
import com.datmt.temporal.workflow.activities.OrderActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication implements CommandLineRunner {

    @Autowired
    private OrderActivityImpl orderActivity;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WorkerHelper.registerWorker(orderActivity);
    }
}
