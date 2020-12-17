package deliveryshippmentjobs;

import deliveryshippmentjobs.model.Entity;
import deliveryshippmentjobs.model.PostEventDTO;
import deliveryshippmentjobs.model.TaskConfiguration;
import deliveryshippmentjobs.odata.ODataDestinationClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.net.HttpURLConnection.HTTP_MULT_CHOICE;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
@AllArgsConstructor
public class PostEventsService {

    private final ODataDestinationClient oDataDestinationClient;

    public void postEvent(PostEventDTO eventDTO, Entity event, TaskConfiguration taskConfiguration) {
        long jobId = taskConfiguration.getJobId();
        try {
            String altKey = eventDTO.getAltKey();
            String reasonCode = taskConfiguration.getReasonCode();
            if (isNotBlank(altKey) && isNotBlank(reasonCode)) {
                int httpStatus = oDataDestinationClient.post(event.getValue(), eventDTO);
                if (httpStatus < HTTP_MULT_CHOICE) {
                    log.info("JOB ID {} : {} Event {} posted successfully: altKey = {}, eventReasonText = {} ", jobId, taskConfiguration.getObjectType(),
                            event.getValue(), altKey, reasonCode);
                } else {
                    log.error("JOB ID {} : Post Event {} failed with Response status {}", jobId, event.getValue(), httpStatus);
                }
            } else {
                log.error("JOB ID {} : Not valid data for event posting - altKey = {}, eventReasonText = {}", jobId, altKey, reasonCode);
            }
        } catch (Exception ex) {
            log.error("JOB ID {} : {} Event posting failed with message : {}", jobId, taskConfiguration.getObjectType(), ex.getMessage());
        }
    }
}
