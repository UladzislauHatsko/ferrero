package deliveryshippmentjobs.shipment;

import deliveryshippmentjobs.model.ShipmentProcess;
import deliveryshippmentjobs.model.ShipmentProcessDeliveriesWrapper;
import deliveryshippmentjobs.model.ShipmentProcessWrapper;
import deliveryshippmentjobs.model.TaskConfiguration;
import deliveryshippmentjobs.odata.ODataDestinationClient;
import deliveryshippmentjobs.odata.querybuilder.ODataQueryBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static deliveryshippmentjobs.model.Entity.SHIPMENT_PROCESS;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static utils.FormatUtils.areStatusEqual;
import static utils.FormatUtils.splitStringListValue;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
@AllArgsConstructor
public class ShipmentProcessService {

    private final ODataDestinationClient oDataDestinationClient;

    private static final String STATUS_970 = "970";
    private static final String STATUS_980 = "980";
    private static final String STATUS_990 = "990";

    public void processShipmentTask(TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        log.info("JOB ID {} : execute shipment job", jobId);

        String shipmentQuery = new ODataQueryBuilder(SHIPMENT_PROCESS).withExpandField("deliveries/delivery").buildUrl();
        List<ShipmentProcess> shipmentProcesses = oDataDestinationClient.get(shipmentQuery, ShipmentProcessWrapper.class).getShipmentProcesses();
        List<ShipmentProcess> shipmentProcessesFiltered = shipmentProcesses
                .stream()
                .filter(it -> isBlank(taskConfiguration.getShipmentStatus()) ||
                        splitStringListValue(taskConfiguration.getShipmentStatus()).stream().anyMatch(status -> areStatusEqual(it.getShipmentStatus(), status)))
                .collect(Collectors.toList());
        log.info("JOB ID {}: {} shipments extracted", jobId, shipmentProcessesFiltered.size());

        shipmentProcessesFiltered.forEach(shipment -> {
            ShipmentProcessDeliveriesWrapper deliveriesWrapper = shipment.getDeliveriesWrapper();
            if (deliveriesWrapper != null) {
                postCompleteShipmentEvent(deliveriesWrapper);
            }
        });
    }

    private void postCompleteShipmentEvent(ShipmentProcessDeliveriesWrapper deliveriesWrapper) {
        List<String> deliveryStatuses = deliveriesWrapper.getShipmentProcessDeliveries()
                .stream()
                .filter(it -> it.getDeliveryProcess() != null)
                .map(it -> it.getDeliveryProcess().getDeliveryStatusCode())
                .distinct()
                .collect(Collectors.toList());
        if (deliveryStatuses.size() == 1) {
            String status = deliveryStatuses.get(0);
            if (areStatusEqual(STATUS_980, status)) {
                //POST SHPTransportationCompletedWithPODEvent
            } else if (areStatusEqual(STATUS_970, status)) {
                //POST SHPTransportationCompletedUnplannedEvent
            }
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_990, status))) {
            //POST SHPTransportationCompletedWithIssuesEvent
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_980, status) || areStatusEqual(STATUS_970, status))) {
            //POST SHPTransportationCompletedUnplannedEvent
        }
    }
}
