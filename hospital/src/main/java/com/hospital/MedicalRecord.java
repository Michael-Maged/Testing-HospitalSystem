package com.hospital;

public class MedicalRecord {
    long recordID;
    long patientID;
    String diagnosis;
    String treatment;

    public MedicalRecord(long recordID, long patientID, String diagnosis, String treatment) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public long getRecordID() {
        return recordID;
    }

    public void setRecordID(long recordID) {
        this.recordID = recordID;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
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
    
    @Override
    public String toString() {
        return "Patient " + patientID + ": Diagnosis: " + diagnosis + ", Treatment: " + treatment;
    }
}
