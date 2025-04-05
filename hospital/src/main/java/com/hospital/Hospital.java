package com.hospital;
import java.util.*;

class Hospital {
    List<Patient> patients = new ArrayList<>();
    List<Appointment> appointments = new ArrayList<>();
    List<MedicalRecord> records = new ArrayList<>();
    List<Bill> bills = new ArrayList<>();
    List<InventoryItem> inventory = new ArrayList<>();

    public void registerPatient(Patient p) {
        patients.add(p);
        System.out.println("Registered patient: " + p.getName());
    }

    public void scheduleAppointment(Appointment a) {
        appointments.add(a);
        System.out.println("Scheduled appointment for patient ID " + a.getPatientID());
    }

    public void addMedicalRecord(MedicalRecord r) {
        records.add(r);
        System.out.println("Added medical record for patient ID " + r.getPatientID());
    }

    public void addInventoryItem(InventoryItem i) {
        inventory.add(i);
        System.out.println("Added inventory item: " + i.getName());
    }

    public void showPatients() {
        for (Patient p : patients) {
            p.toString();
        }
    }
}
