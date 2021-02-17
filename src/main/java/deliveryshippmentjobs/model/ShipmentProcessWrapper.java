package deliveryshippmentjobs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.List;

/**
 * @author uladzislau.hatsko
 */
@JsonRootName("d")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentProcessWrapper {

    @JsonProperty("results")
    private List<ShipmentProcess> shipmentProcesses;

    @JsonProperty("__count")
    private int inlineCount;
}
