package deliveryshippmentjobs.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Entity {

    DELIVERY_PROCESS("DeliveryProcess"),
    SHIPMENT_PROCESS("ShipmentProcess"),
    SHIPMENT_PROCESS_DELIVERIES("ShipmentProcessDeliveries"),
    DELIVERY_COMPLETED_EVENT("DLVDeliveryCompletedEvent"),
    SHIPMENT_COMPLETED_WITH_POD_EVENT("SHPTransportationCompletedWithPODEvent"),
    SHIPMENT_COMPLETED_WITH_ISSUES_EVENT("SHPTransportationCompletedWithIssuesEvent"),
    SHIPMENT_COMPLETED_UNPLANNED_EVENT("SHPTransportationCompletedUnplannedEvent");

    @Getter
    private final String value;

    Entity(String value) {
        this.value = value;
    }
}
