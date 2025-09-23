package com.extropos.java.entity;

/**
 * TableService entity represents table service information and status
 */
public class TableService {
    private long id;
    private int tableId;
    private String tableName;
    private String status; // AVAILABLE, OCCUPIED, RESERVED, CLEANING, MAINTENANCE
    private String waiterId;
    private String waiterName;
    private String customerName;
    private String customerPhone;
    private long serviceStartTime;
    private long serviceEndTime;
    private long reservationTime;
    private String specialRequests;
    private String notes;
    private double estimatedBill;
    private int guestCount;

    // Status constants
    public static final String STATUS_AVAILABLE = "AVAILABLE";
    public static final String STATUS_OCCUPIED = "OCCUPIED";
    public static final String STATUS_RESERVED = "RESERVED";
    public static final String STATUS_CLEANING = "CLEANING";
    public static final String STATUS_MAINTENANCE = "MAINTENANCE";

    public TableService() {
        this.status = STATUS_AVAILABLE;
        this.serviceStartTime = 0;
        this.serviceEndTime = 0;
        this.reservationTime = 0;
        this.estimatedBill = 0.0;
        this.guestCount = 0;
    }

    public TableService(int tableId, String tableName) {
        this();
        this.tableId = tableId;
        this.tableName = tableName;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getWaiterId() { return waiterId; }
    public void setWaiterId(String waiterId) { this.waiterId = waiterId; }

    public String getWaiterName() { return waiterName; }
    public void setWaiterName(String waiterName) { this.waiterName = waiterName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public long getServiceStartTime() { return serviceStartTime; }
    public void setServiceStartTime(long serviceStartTime) { this.serviceStartTime = serviceStartTime; }

    public long getServiceEndTime() { return serviceEndTime; }
    public void setServiceEndTime(long serviceEndTime) { this.serviceEndTime = serviceEndTime; }

    public long getReservationTime() { return reservationTime; }
    public void setReservationTime(long reservationTime) { this.reservationTime = reservationTime; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public double getEstimatedBill() { return estimatedBill; }
    public void setEstimatedBill(double estimatedBill) { this.estimatedBill = estimatedBill; }

    public int getGuestCount() { return guestCount; }
    public void setGuestCount(int guestCount) { this.guestCount = guestCount; }

    // Utility methods
    public boolean isAvailable() {
        return STATUS_AVAILABLE.equals(status);
    }

    public boolean isOccupied() {
        return STATUS_OCCUPIED.equals(status);
    }

    public boolean isReserved() {
        return STATUS_RESERVED.equals(status);
    }

    public boolean isCleaning() {
        return STATUS_CLEANING.equals(status);
    }

    public boolean isMaintenance() {
        return STATUS_MAINTENANCE.equals(status);
    }

    public long getServiceDuration() {
        if (serviceStartTime > 0 && serviceEndTime > 0) {
            return serviceEndTime - serviceStartTime;
        } else if (serviceStartTime > 0) {
            return System.currentTimeMillis() - serviceStartTime;
        }
        return 0;
    }

    public boolean hasActiveService() {
        return serviceStartTime > 0 && serviceEndTime == 0;
    }

    @Override
    public String toString() {
        return "TableService{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", status='" + status + '\'' +
                ", waiterName='" + waiterName + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}