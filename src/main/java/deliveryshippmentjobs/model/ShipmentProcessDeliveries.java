package deliveryshippmentjobs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author uladzislau.hatsko
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentProcessDeliveries {

    @JsonProperty("deliveryId")
    private String deliveryId;

    @JsonProperty("deliveryAltKey")
    private String deliveryAltKey;

    @JsonProperty("delivery_id")
    private String deliveryIdForeign;

    @JsonProperty("shipmentProcess_id")
    private String shipmentProcessIdForeign;

    @JsonProperty("delivery")
    private DeliveryProcess deliveryProcess;

}
