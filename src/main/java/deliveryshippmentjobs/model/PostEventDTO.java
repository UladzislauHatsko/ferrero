package deliveryshippmentjobs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.serializer.JsonInstantSerializer;

import java.time.Instant;

/**
 * @author uladzislau.hatsko
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostEventDTO {

    @JsonProperty("altKey")
    private String altKey;
    @JsonProperty("actualBusinessTimestamp")
    @JsonSerialize(using = JsonInstantSerializer.class)
    private Instant timestamp;
    @JsonProperty("actualBusinessTimeZone")
    private String timezone;
    @JsonProperty("eventReasonText")
    private String reason;

    public PostEventDTO(String altKey, String reason) {
        this.altKey = altKey;
        this.reason = reason;
        this.timestamp = Instant.now();
        this.timezone = "UTC";
    }
}
