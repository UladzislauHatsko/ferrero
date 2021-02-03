package deliveryshippmentjobs.model;

import java.util.List;

/**
 * @author uladzislau.hatsko
 */
public class TaskConfiguration {

    private String jobId;
    private String objectType;
    private String deliveryType;
    private String shippingPoint;
    private String deliveryStatus;
    private Integer daysBack;
    private String shipmentStatus;
    private String reasonCode;
    private String referenceDate;
    private String vehicleType;
    private List<String> cron;

    public TaskConfiguration() {

    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public List<String> getCron() {
        return cron;
    }

    public void setCron(List<String> cron) {
        this.cron = cron;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public String getDeliveryType() {
        return this.deliveryType;
    }

    public String getShippingPoint() {
        return this.shippingPoint;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public Integer getDaysBack() {
        return this.daysBack;
    }

    public String getShipmentStatus() {
        return this.shipmentStatus;
    }

    public String getReasonCode() {
        return this.reasonCode;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setShippingPoint(String shippingPoint) {
        this.shippingPoint = shippingPoint;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void setDaysBack(Integer daysBack) {
        this.daysBack = daysBack;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
