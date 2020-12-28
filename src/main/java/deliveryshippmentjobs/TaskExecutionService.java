package deliveryshippmentjobs;

import deliveryshippmentjobs.delivery.DeliveryProcessService;
import deliveryshippmentjobs.model.TaskConfiguration;
import deliveryshippmentjobs.shipment.ShipmentProcessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author uladzislau.hatsko
 */
@Slf4j
@Service
@AllArgsConstructor
public class TaskExecutionService {

    public static final String DELIVERY_DATE = "DELIVERY_DATE";
    public static final String GOODS_ISSUE_DATE = "GOODS_ISSUE_DATE";
    public static final String DELIVERY = "DELIVERY";
    public static final String SHIPMENT = "SHIPMENT";

    private final DeliveryProcessService deliveryProcessService;
    private final ShipmentProcessService shipmentProcessService;

    public void executeTask(TaskConfiguration taskConfiguration) {
        log.info("JOB ID {} : start execution", taskConfiguration.getJobId());

        if (DELIVERY.equals(taskConfiguration.getObjectType())) {
            deliveryProcessService.processDeliveryTask(taskConfiguration);
        }

        if (SHIPMENT.equals(taskConfiguration.getObjectType())) {
            shipmentProcessService.processShipmentTask(taskConfiguration);
        }

    }
}
