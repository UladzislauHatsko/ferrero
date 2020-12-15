package deliveryshippmentjobs.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Entity {

    DELIVERY_PROCESS("DeliveryProcess"),
    SHIPMENT_PROCESS("ShipmentProcess"),
    SHIPMENT_PROCESS_DELIVERIES("ShipmentProcessDeliveries");

    @Getter
    private final String value;

    Entity(String value) {
        this.value = value;
    }
}
