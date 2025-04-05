package com.hospital;

import java.util.Date;

public class Bill {
    long billId;
    double amount;
    Date billingDate;

    public Bill(long billId, double amount , Date billingDate) {
        this.billId = billId;
        this.amount = amount;
        this.billingDate = billingDate;
    }

    public String toString() {
        return ("Bill ID: " + billId + ", Amount: $" + amount + "Billing Date: " + billingDate);
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
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
