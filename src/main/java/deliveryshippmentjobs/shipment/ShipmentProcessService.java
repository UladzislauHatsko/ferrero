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
        long jobId = taskConfiguration.getJobId();
        log.info("JOB ID {} : execute shipment job", jobId);

        List<ShipmentProcess> shipmentProcesses = readEntitiesService.readShipments(taskConfiguration);
        log.info("JOB ID {} : {} shipments extracted", jobId, shipmentProcesses.size());
//        Set<String> altKeys = shipmentProcesses.stream().map(ShipmentProcess::getAltKey).collect(Collectors.toSet());
//        log.info("JOB ID {} : Shipments altKeys extracted {}", jobId, String.join(", ", altKeys));

//        shipmentProcesses.forEach(shipment -> {
//            ShipmentProcessDeliveriesWrapper deliveriesWrapper = shipment.getDeliveriesWrapper();
//            if (deliveriesWrapper != null) {
//                postCompleteShipmentEvent(shipment, deliveriesWrapper, taskConfiguration);
//            }
//        });
        postDevEvents(taskConfiguration);
    }

    private void postDevEvents(TaskConfiguration taskConfiguration) {
        PostEventDTO eventDTO = new PostEventDTO("xri://sap.com/id:LBN#10010001316:KE7CLNT500:ZCT_SHP_NO:0052906511", taskConfiguration.getReasonCode());
        postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_WITH_POD_EVENT, taskConfiguration);

        PostEventDTO eventDTO2 = new PostEventDTO("xri://sap.com/id:LBN#10010001316:KE7CLNT500:ZCT_SHP_NO:0052906600", taskConfiguration.getReasonCode());
        postEventsService.postEvent(eventDTO2, SHIPMENT_COMPLETED_UNPLANNED_EVENT, taskConfiguration);
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
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_990, status))) {
            postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_WITH_ISSUES_EVENT, taskConfiguration);
        } else if (deliveryStatuses.stream().anyMatch(status -> areStatusEqual(STATUS_980, status) || areStatusEqual(STATUS_970, status))) {
            postEventsService.postEvent(eventDTO, SHIPMENT_COMPLETED_UNPLANNED_EVENT, taskConfiguration);
        } else {
            log.info("JOB ID {} : ShipmentProcess with altKey {} still has opened deliveries", taskConfiguration.getJobId(), shipment.getAltKey());
        }
    }
}
