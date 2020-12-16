package deliveryshippmentjobs;

import deliveryshippmentjobs.model.DeliveryProcess;
import deliveryshippmentjobs.model.DeliveryProcessWrapper;
import deliveryshippmentjobs.model.ShipmentProcess;
import deliveryshippmentjobs.model.ShipmentProcessWrapper;
import deliveryshippmentjobs.model.TaskConfiguration;
import deliveryshippmentjobs.odata.ODataDestinationClient;
import deliveryshippmentjobs.odata.querybuilder.FilterOperator;
import deliveryshippmentjobs.odata.querybuilder.ODataQueryBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static deliveryshippmentjobs.TaskExecutionService.DELIVERY_DATE;
import static deliveryshippmentjobs.TaskExecutionService.GOODS_ISSUE_DATE;
import static deliveryshippmentjobs.model.DeliveryProcess.DELIVERY_TYPE;
import static deliveryshippmentjobs.model.DeliveryProcess.PLAN_LOADING_DATE;
import static deliveryshippmentjobs.model.DeliveryProcess.PLAN_UNLOADING_DATE;
import static deliveryshippmentjobs.model.Entity.DELIVERY_PROCESS;
import static deliveryshippmentjobs.model.Entity.SHIPMENT_PROCESS;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static utils.FormatUtils.areStatusEqual;
import static utils.FormatUtils.splitStringListValue;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
@AllArgsConstructor
public class ReadEntitiesService {

    private final ODataDestinationClient oDataDestinationClient;

    public List<DeliveryProcess> readDeliveries(TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        try {
            final String deliveryType = taskConfiguration.getDeliveryType();
            final Integer daysBack = taskConfiguration.getDaysBack();
            final String referenceDate = taskConfiguration.getReferenceDate();
            final String shippingPointConfiguration = taskConfiguration.getShippingPoint();
            final String statusStringConfiguration = taskConfiguration.getDeliveryStatus();
            log.info(
                    "JOB ID {} : Extract DeliveryProcess by criteria deliveryTypes = {}, daysBack = {}, referenceDate = {}, shippingPoints = {}, statuses = {}",
                    jobId, deliveryType, daysBack, referenceDate, shippingPointConfiguration, statusStringConfiguration);

            ODataQueryBuilder queryBuilder = new ODataQueryBuilder(DELIVERY_PROCESS);
            if (isNotBlank(deliveryType)) {
                queryBuilder.withFilterIn(DELIVERY_TYPE, splitStringListValue(deliveryType));
            }

            if (daysBack == null || referenceDate == null) {
                log.warn("JOB ID {} : DaysBack or ReferenceDate is not configured", jobId);
            } else {
                Instant referenceInstant = Instant.now().minus(daysBack, DAYS);
                if (DELIVERY_DATE.equals(referenceDate)) {
                    queryBuilder.withFilter(PLAN_UNLOADING_DATE, FilterOperator.LESS_EQUALS, referenceInstant);
                } else if (GOODS_ISSUE_DATE.equals(referenceDate)) {
                    queryBuilder.withFilter(PLAN_LOADING_DATE, FilterOperator.LESS_EQUALS, referenceInstant);
                }
            }

            String deliveryQuery = queryBuilder.buildUrl();
            List<DeliveryProcess> deliveryProcesses = oDataDestinationClient.get(deliveryQuery, DeliveryProcessWrapper.class).getDeliveryProcesses();

            return deliveryProcesses.stream().filter(it -> {

                boolean hasValidShippingPoint = isBlank(shippingPointConfiguration) ||
                        splitStringListValue(shippingPointConfiguration).stream().anyMatch(point -> StringUtils.equals(point, it.getShippingPoint()));
                boolean hasValidStatus = isBlank(statusStringConfiguration) ||
                        splitStringListValue(statusStringConfiguration).stream().anyMatch(status -> areStatusEqual(it.getDeliveryStatusCode(), status));
                return hasValidShippingPoint && hasValidStatus;
            }).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("JOB ID {} : Reading DeliveryProcess failed with message {}", jobId, ex.getMessage());
            return emptyList();
        }
    }

    public List<ShipmentProcess> readShipments(TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        try {
            log.info("JOB ID {} : Extract ShipmentProcess by criteria: statuses = {}", jobId, taskConfiguration.getShipmentStatus());
            String shipmentQuery = new ODataQueryBuilder(SHIPMENT_PROCESS).withExpandField("deliveries/delivery").buildUrl();
            List<ShipmentProcess> shipmentProcesses = oDataDestinationClient.get(shipmentQuery, ShipmentProcessWrapper.class).getShipmentProcesses();
            return shipmentProcesses
                    .stream()
                    .filter(it -> isBlank(taskConfiguration.getShipmentStatus()) ||
                            splitStringListValue(taskConfiguration.getShipmentStatus()).stream()
                                    .anyMatch(status -> areStatusEqual(it.getShipmentStatus(), status)))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("JOB ID {} : Reading ShipmentProcess failed with message {}", jobId, ex.getMessage());
            return emptyList();
        }
    }
}
