package com.extropos.java.models;

import java.util.Date;

public class DailyReportData {
    private Date date;
    private String period;
    private int orderCount;
    private double totalAmount;
    private double taxAmount;
    private String paymentMethod;
    private String categoryName;

    public DailyReportData() {
    }

    public DailyReportData(Date date, String period, int orderCount, 
                          double totalAmount, double taxAmount, 
                          String paymentMethod, String categoryName) {
        this.date = date;
        this.period = period;
        this.orderCount = orderCount;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.paymentMethod = paymentMethod;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}