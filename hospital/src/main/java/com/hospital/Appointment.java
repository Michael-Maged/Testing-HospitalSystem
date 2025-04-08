package com.hospital;
import java.sql.Time;
import java.util.*;

public class Appointment {
    int appID;
    int patientID;
    int docID;
    String type;
    Date date;
    Time time;
    
    public Appointment(int appID, int patientID, String type, Date date, Time time, int docID){
        this.appID = appID;
        this.patientID = patientID;
        this.docID = docID;
        this.type = type;
        this.date = date;
        this.time = time;
    }
    
    public int getAppID() {
        return appID;
    }
    
    public void setAppID(int appID) {
        this.appID = appID;
    }
    
    public int getPatientID() {
        return patientID;
    }
    
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
    
    public int getDocID() {
        return docID;
    }
    
    public void setDocID(int docID) {
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
    
    @Override
    public String toString(){
        return "Appointment for patient " + patientID + " with Dr. " + docID + " on " + date;
    }
}

