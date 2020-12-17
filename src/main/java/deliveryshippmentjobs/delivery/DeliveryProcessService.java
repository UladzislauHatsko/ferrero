package deliveryshippmentjobs.delivery;

import deliveryshippmentjobs.PostEventsService;
import deliveryshippmentjobs.ReadEntitiesService;
import deliveryshippmentjobs.model.DeliveryProcess;
import deliveryshippmentjobs.model.PostEventDTO;
import deliveryshippmentjobs.model.TaskConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static deliveryshippmentjobs.model.Entity.DELIVERY_COMPLETED_EVENT;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
@AllArgsConstructor
public class DeliveryProcessService {

    private final PostEventsService postEventsService;
    private final ReadEntitiesService readEntitiesService;

    public void processDeliveryTask(TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        log.info("JOB ID {} : execute delivery job", jobId);

        List<DeliveryProcess> deliveryProcesses = readEntitiesService.readDeliveries(taskConfiguration);
        log.info("JOB ID {} : {} DeliveryProcesses extracted", jobId, deliveryProcesses.size());
        Set<String> altKeys = deliveryProcesses.stream().map(DeliveryProcess::getAltKey).collect(Collectors.toSet());
        log.debug("JOB ID {} : DeliveryProcess altKeys extracted : {}", jobId, String.join(", ", altKeys));

        postDeliveryCompleteEvent(taskConfiguration, deliveryProcesses);
    }

    private void postDeliveryCompleteEvent(TaskConfiguration taskConfiguration, List<DeliveryProcess> deliveryProcessesFiltered) {
        deliveryProcessesFiltered.forEach(it -> {
            PostEventDTO eventDTO = new PostEventDTO(it.getAltKey(), taskConfiguration.getReasonCode());
            postEventsService.postEvent(eventDTO, DELIVERY_COMPLETED_EVENT, taskConfiguration);
        });
    }
}
