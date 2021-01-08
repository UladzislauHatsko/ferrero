package deliveryshippmentjobs.shipment;

import deliveryshippmentjobs.PostEventsService;
import deliveryshippmentjobs.ReadEntitiesService;
import deliveryshippmentjobs.model.PostEventDTO;
import deliveryshippmentjobs.model.ShipmentProcess;
import deliveryshippmentjobs.model.ShipmentProcessDeliveriesWrapper;
import deliveryshippmentjobs.model.TaskConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static deliveryshippmentjobs.model.Entity.SHIPMENT_COMPLETED_UNPLANNED_EVENT;
import static deliveryshippmentjobs.model.Entity.SHIPMENT_COMPLETED_WITH_ISSUES_EVENT;
import static deliveryshippmentjobs.model.Entity.SHIPMENT_COMPLETED_WITH_POD_EVENT;
import static utils.FormatUtils.areStatusEqual;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
@AllArgsConstructor
public class ShipmentProcessService {

    private final PostEventsService postEventsService;
    private final ReadEntitiesService readEntitiesService;

    private static final String STATUS_970 = "970";
    private static final String STATUS_980 = "980";
    private static final String STATUS_990 = "990";

    public void processShipmentTask(TaskConfiguration taskConfiguration) {
        String jobId = taskConfiguration.getJobId();
        log.info("JOB ID {} : execute shipment job", jobId);

        List<ShipmentProcess> shipmentProcesses = readEntitiesService.readShipments(taskConfiguration);
        log.info("JOB ID {} : {} shipments extracted", jobId, shipmentProcesses.size());
        Set<String> altKeys = shipmentProcesses.stream().map(ShipmentProcess::getTrackingId).collect(Collectors.toSet());
        log.debug("JOB ID {} : Shipments trackingIds extracted {}", jobId, String.join(", ", altKeys));

        shipmentProcesses.stream()
                .forEach(shipment -> {
                    ShipmentProcessDeliveriesWrapper deliveriesWrapper = shipment.getDeliveriesWrapper();
                    if (deliveriesWrapper != null) {
                        postCompleteShipmentEvent(shipment, deliveriesWrapper, taskConfiguration);
                    }
                });
    }

    private void postCompleteShipmentEvent(ShipmentProcess shipment, ShipmentProcessDeliveriesWrapper deliveriesWrapper, TaskConfiguration taskConfiguration) {
        List<String> deliveryStatuses = deliveriesWrapper.getShipmentProcessDeliveries()
                .stream()
                .filter(it -> it.getDeliveryProcess() != null)
                .map(it -> it.getDeliveryProcess().getDeliveryStatusCode())
                .distinct()
                .collect(Collectors.toList());
        PostEventDTO eventDTO = new PostEventDTO(shipment.getAltKey(), taskConfiguration.getReasonCode());
        if (deliveryStatuses.size() == 1) {
            String status = deliveryStatuses.get(0);
            if (areStatusEqual(STATUS_980, status)) {
                postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_WITH_POD_EVENT, taskConfiguration);
            } else if (areStatusEqual(STATUS_970, status)) {
                postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_UNPLANNED_EVENT, taskConfiguration);
            }
        } else if (deliveryStatuses.stream()
                .anyMatch(status -> !areStatusEqual(STATUS_980, status) && !areStatusEqual(STATUS_970, status) && !areStatusEqual(STATUS_990, status))) {
            log.info("JOB ID {} : ShipmentProcess with trackingId {} still has opened deliveries", taskConfiguration.getJobId(), shipment.getTrackingId());
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_990, status))) {
            postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_WITH_ISSUES_EVENT, taskConfiguration);
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_980, status) || areStatusEqual(STATUS_970, status))) {
            postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_UNPLANNED_EVENT, taskConfiguration);
        }
    }
}
