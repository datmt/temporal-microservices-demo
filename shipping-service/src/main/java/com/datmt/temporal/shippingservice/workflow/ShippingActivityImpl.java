package com.datmt.temporal.shippingservice.workflow;

import com.datmt.temporal.shippingservice.models.Shipment;
import com.datmt.temporal.shippingservice.repositories.ShipmentRepository;
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
    public void shipOrder(Long orderId) {
        log.info("Shipping order {}", orderId);
        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setStatus("SHIPPED");
        shipmentRepository.save(shipment);
    }
}
