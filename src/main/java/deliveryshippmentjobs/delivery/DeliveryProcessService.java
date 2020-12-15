package deliveryshippmentjobs.delivery;

import deliveryshippmentjobs.model.DeliveryProcess;
import deliveryshippmentjobs.model.DeliveryProcessWrapper;
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
import static java.time.temporal.ChronoUnit.DAYS;
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
public class DeliveryProcessService {

    private final ODataDestinationClient oDataDestinationClient;

    public void processDeliveryTask(TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        log.info("JOB ID {} : execute delivery job", jobId);

        String deliveryType = taskConfiguration.getDeliveryType();
        ODataQueryBuilder queryBuilder = new ODataQueryBuilder(DELIVERY_PROCESS);
        if (isNotBlank(deliveryType)) {
            queryBuilder.withFilterIn(DELIVERY_TYPE, splitStringListValue(deliveryType));
        }
        Integer daysBack = taskConfiguration.getDaysBack();
        String referenceDate = taskConfiguration.getReferenceDate();
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
        List<DeliveryProcess> deliveryProcessesFiltered = deliveryProcesses.stream().filter(it -> {
            String shippingPointConfiguration = taskConfiguration.getShippingPoint();
            String statusStringConfiguration = taskConfiguration.getDeliveryStatus();
            boolean hasValidShippingPoint = isBlank(shippingPointConfiguration) ||
                    splitStringListValue(shippingPointConfiguration).stream().anyMatch(point -> StringUtils.equals(point, it.getShippingPoint()));
            boolean hasValidStatus = isBlank(statusStringConfiguration) ||
                    splitStringListValue(statusStringConfiguration).stream().anyMatch(status -> areStatusEqual(it.getDeliveryStatusCode(), status));
            return hasValidShippingPoint && hasValidStatus;
        }).collect(Collectors.toList());
        log.info("JOB ID {} : {} DeliveryProcesses extracted", jobId, deliveryProcessesFiltered.size());

        postDeliveryCompleteEvent(deliveryProcessesFiltered);
    }

    private void postDeliveryCompleteEvent(List<DeliveryProcess> deliveryProcessesFiltered) {
        deliveryProcessesFiltered.forEach(it -> {
            //POST DLVDeliveryCompletedEvent
        });
    }
}
