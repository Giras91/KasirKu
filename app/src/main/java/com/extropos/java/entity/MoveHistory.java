package com.extropos.java.entity;

public class MoveHistory {
    private String moveId;
    private String orderId;
    private String fromTable;
    private String toTable;
    private String movedOn;

    public MoveHistory() {}

    public String getMoveId() { return moveId; }
    public void setMoveId(String moveId) { this.moveId = moveId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getFromTable() { return fromTable; }
    public void setFromTable(String fromTable) { this.fromTable = fromTable; }

    public String getToTable() { return toTable; }
    public void setToTable(String toTable) { this.toTable = toTable; }

    public String getMovedOn() { return movedOn; }
    public void setMovedOn(String movedOn) { this.movedOn = movedOn; }
}
