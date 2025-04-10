package com.hospital;

import java.util.*;

public class MedicalRecord {
    int recordID;
    int patientID;
    String diagnosis;
    String treatment;
    Date date;

    public MedicalRecord(int recordID, int patientID, String diagnosis, String treatment, Date date) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Patient " + patientID + ": Diagnosis: " + diagnosis + ", Treatment: " + treatment;
    }
}
