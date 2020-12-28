package deliveryshippmentjobs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author uladzislau.hatsko
 */
@Entity
@Table(name = "JOB_CONFIGURATION")
public class TaskConfiguration {

    @Id
    @Column(name = "JOB_ID")
    private String jobId;
    @Column(name = "OBJECT_TYPE")
    private String objectType;
    @Column(name = "DELIVERY_TYPE")
    private String deliveryType;
    @Column(name = "SHIPPING_POINT")
    private String shippingPoint;
    @Column(name = "DELIVERY_STATUS")
    private String deliveryStatus;
    @Column(name = "DAYS_BACK")
    private Integer daysBack;
    @Column(name = "SHIPMENT_STATUS")
    private String shipmentStatus;
    @Column(name = "REASON_CODE")
    private String reasonCode;
    @Column(name = "REFERENCE_DATE")
    private String referenceDate;
    @Column(name = "CRON")
    private String cron;

    public TaskConfiguration() {

    }

    public TaskConfiguration(String jobId, String objectType, String deliveryType, String shippingPoint, String deliveryStatus, Integer daysBack,
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
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
