package deliveryshippmentjobs.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author uladzislau.hatsko
 */
@Entity
public class TaskConfiguration {

    @Id
    private long jobId;
    private String objectType;
    private String deliveryType;
    private String shippingPoint;
    private String deliveryStatus;
    private Integer daysBack;
    private String shipmentStatus;
    private String reasonCode;
    private String referenceDate;
    private String cron;

    public TaskConfiguration(long jobId, String objectType, String cron, String deliveryType, Integer daysBack, String referenceDate, String shippingPoint,
            String deliveryStatus) {
        this.jobId = jobId;
        this.cron = cron;
        this.deliveryType = deliveryType;
        this.daysBack = daysBack;
        this.referenceDate = referenceDate;
        this.shippingPoint = shippingPoint;
        this.deliveryStatus = deliveryStatus;
        this.objectType = objectType;
    }

    public TaskConfiguration(long jobId, String objectType, String cron) {
        this.jobId = jobId;
        this.cron = cron;
        this.objectType = objectType;
    }

    public TaskConfiguration() {

    }

    public TaskConfiguration(long jobId, String objectType, String deliveryType, String shippingPoint, String deliveryStatus, Integer daysBack,
            String shipmentStatus, String reasonCode, String cron) {
        this.jobId = jobId;
        this.objectType = objectType;
        this.deliveryType = deliveryType;
        this.shippingPoint = shippingPoint;
        this.deliveryStatus = deliveryStatus;
        this.daysBack = daysBack;
        this.shipmentStatus = shipmentStatus;
        this.reasonCode = reasonCode;
        this.cron = cron;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
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
}