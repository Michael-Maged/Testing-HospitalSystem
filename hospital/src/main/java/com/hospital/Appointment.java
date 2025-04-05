package com.hospital;
import java.sql.Time;
import java.util.*;

class Appointment {
    long appID;
    long patientID;
    long docID;
    String type;
    Date date;
    Time time;
    
    Bill bill;
    
    public Appointment(long appID, long patientID, long docID, String type, Date date, Time time , Bill bill){
        this.appID = appID;
        this.patientID = patientID;
        this.docID = docID;
        this.type = type;
        this.date = date;
        this.time = time;
        this.bill = bill;
    }
    
    public long getAppID() {
        return appID;
    }
    
    public void setAppID(long appID) {
        this.appID = appID;
    }
    
    public long getPatientID() {
        return patientID;
    }
    
    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }
    
    public long getDocID() {
        return docID;
    }
    
    public void setDocID(long docID) {
        this.docID = docID;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Time getTime() {
        return time;
    }
    
    public void setTime(Time time) {
        this.time = time;
    }
    
    public Bill getBill() {
        return bill;
    }
    
    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    @Override
    public String toString(){
        return "Appointment for patient " + patientID + " with Dr. " + docID + " on " + date;
    }
}

