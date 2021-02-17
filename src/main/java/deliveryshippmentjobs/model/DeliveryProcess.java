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
public class DeliveryProcess {

    public static final String DELIVERY_TYPE = "deliveryType";
    public static final String PLAN_UNLOADING_DATE = "planUnloadingDate";
    public static final String PLAN_LOADING_DATE = "planLoadingDate";
    public static final String DELIVERY_STATUS_CODE = "deliveryStatus_code";
    public static final String SHIPPING_POINT = "shippingPoint";

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
    private Integer version;

    @JsonProperty("createdOnDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date createdOnDate;

    @JsonProperty("createdOnTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date createdOnTimestamp;

    @JsonProperty("purchaseOrderId")
    private String purchaseOrderId;

    @JsonProperty("dataLogId")
    private String dataLogId;

    @JsonProperty("deliveryDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date deliveryDate;

    @JsonProperty("deliveryDateTxt")
    private String deliveryDateTxt;

    @JsonProperty("deliveryPriority")
    private String deliveryPriority;

    @JsonProperty(DELIVERY_TYPE)
    private String deliveryType;

    @JsonProperty("deliveryTypeTxt")
    private String deliveryTypeTxt;

    @JsonProperty("loadingDate")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date loadingDate;

    @JsonProperty("numberOfPackages")
    private Integer numberOfPackages;

    @JsonProperty("salesOrderId")
    private String salesOrderId;

    @JsonProperty("salesOrderType")
    private String salesOrderType;

    @JsonProperty("planLoadingTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planLoadingTimestamp;

    @JsonProperty("planLoadingDateTxt")
    private String planLoadingDateTxt;

    @JsonProperty(PLAN_LOADING_DATE)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planLoadingDate;

    @JsonProperty("planUnloadingTimestamp")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planUnloadingTimestamp;

    @JsonProperty("planUnloadingDateTxt")
    private String planUnloadingDateTxt;

    @JsonProperty(PLAN_UNLOADING_DATE)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date planUnloadingDate;

    @JsonProperty("salesOrganization")
    private String salesOrganization;

    @JsonProperty(SHIPPING_POINT)
    private String shippingPoint;

    @JsonProperty("shippingType")
    private String shippingType;

    @JsonProperty("deliveryId")
    private String deliveryId;

    @JsonProperty("processStatus_code")
    private String processStatusCode;

    @JsonProperty("lifeCycleStatus_code")
    private String lifeCycleStatusCode;

    @JsonProperty("deliveryLifeCycleStatus_code")
    private String deliveryLifeCycleStatusCode;

    @JsonProperty(DELIVERY_STATUS_CODE)
    private String deliveryStatusCode;

    @JsonProperty("vehicleType")
    private String vehicleType;
}
