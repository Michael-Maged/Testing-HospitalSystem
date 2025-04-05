package com.hospital;

import java.util.Date;

public class Bill {
    long billId;
    long patientId;
    double amount;
    Date billingDate;

    public Bill(long billId, long patientId, double amount) {
        this.billId = billId;
        this.patientId = patientId;
        this.amount = amount;
        this.billingDate = billingDate;
    }

    public void display() {
        System.out.println("Bill ID: " + billId + ", Amount: $" + amount);
        System.out.println("Billing Date: " + billingDate);
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }
  
}
