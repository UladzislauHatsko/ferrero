package deliveryshippmentjobs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import utils.deserializer.JsonDateDeserializer;

import java.util.Date;

/**
 * @author uladzislau.hatsko
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentProcess {

    public static final String SHIPMENT_STATUS_CODE = "shipmentStatus_code";
    @JsonProperty("id")
    private String id;

    @JsonProperty("subaccountId")
    private String subaccountId;

    @JsonProperty("cloneInstanceId")
    private String cloneInstanceId;

    @JsonProperty("trackedProcessType")
    private String trackedProcessType;

    @JsonProperty("altKey")
    private String altKey;

    @JsonProperty("scheme")
    private String scheme;

    @JsonProperty("partyId")
    private String partyId;

    @JsonProperty("logicalSystem")
    private String logicalSystem;

    @JsonProperty("trackingIdType")
    private String trackingIdType;

    @JsonProperty("trackingId")
    private String trackingId;

    @JsonProperty("lastChangedAtBusinessTime")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date lastChangedAtBusinessTime;

    @JsonProperty("createdByUser")
    private String createdByUser;

    @JsonProperty("creationDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date creationDateTime;

    @JsonProperty("lastChangedByUser")
    private String lastChangedByUser;

    @JsonProperty("lastChangeDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date lastChangeDateTime;

    @JsonProperty("version")
    private String version;

    @JsonProperty("createdOnDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date createdOnDate;

    @JsonProperty("createdOnTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date createdOnTimestamp;

    @JsonProperty("createdOnDateTxt")
    private String createdOnDateTxt;

    @JsonProperty("planLoadingTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planLoadingTimestamp;

    @JsonProperty("planLoadingDateTxt")
    private String planLoadingDateTxt;

    @JsonProperty("planLoadingDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planLoadingDate;

    @JsonProperty("planUnloadingTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planUnloadingTimestamp;

    @JsonProperty("planUnloadingDateTxt")
    private String planUnloadingDateTxt;

    @JsonProperty("planUnloadingDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planUnloadingDate;

    @JsonProperty("shipmentType")
    private String shipmentType;

    @JsonProperty("shippingType")
    private String shippingType;

    @JsonProperty("specialProcessInd")
    private String specialProcessInd;

    @JsonProperty("shipmentId")
    private String shipmentId;

    @JsonProperty("processStatus_code")
    private String processStatus;

    @JsonProperty(SHIPMENT_STATUS_CODE)
    private String shipmentStatus;

    @JsonProperty("deliveries")
    private ShipmentProcessDeliveriesWrapper deliveriesWrapper;
}
