package com.datmt.temporal.shippingservice.workflow;

import com.datmt.temporal.shippingservice.models.Shipment;
import com.datmt.temporal.shippingservice.repositories.ShipmentRepository;
import com.datmt.temporal.workflow.activities.ShippingActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShippingActivityImpl implements ShippingActivity {
    private ShipmentRepository shipmentRepository;

    @Autowired
    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public void processShipment(Long orderId) {
        log.info("Shipment processed for order id: {}:", orderId);
        var shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setStatus("SHIPPED");
        shipmentRepository.save(shipment);

        log.info("Shipment saved successfully for order id: {}", orderId);
    }

}
