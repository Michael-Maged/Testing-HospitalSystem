package com.hospital;

import java.util.Date;

public class Bill {
    int patientID;
    int billId;
    double amount;
    Date billingDate;

    public Bill(int patientID, int billId, double amount, Date billingDate) {
        this.patientID = patientID;
        this.billId = billId;
        this.amount = amount;
        this.billingDate = billingDate;
    }

    public String toString() {
        return ("Bill ID: " + billId + ", Amount: $" + amount + "Billing Date: " + billingDate);
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

}
