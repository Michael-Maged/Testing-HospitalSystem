package com.hospital;

import java.util.Date;

public class Bill {
    int billId;
    double amount;
    Date billingDate;

    public Bill(int billId, double amount , Date billingDate) {
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
  
}
