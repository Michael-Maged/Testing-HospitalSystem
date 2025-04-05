package com.hospital;
import java.util.*;
import java.sql.*;
import java.sql.Date;

class Hospital {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
    private static final String USER = "johnnazizz";
    private static final String PASSWORD = "blabla1";

    List<Patient> patients = new ArrayList<>();
    List<Appointment> appointments = new ArrayList<>();
    List<MedicalRecord> records = new ArrayList<>();
    List<Bill> bills = new ArrayList<>();
    List<InventoryItem> inventory = new ArrayList<>();

    public void registerPatient(int id, String name, int age, String gender, String address, String phone) {
        String query = "INSERT INTO Patients (id, name, age, gender, address, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setString(4, gender);
            stmt.setString(5, address);
            stmt.setString(6, phone);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient registered successfully!");
            } else {
                System.out.println("Failed to register patient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void scheduleAppointment(int appID, long patientID, String type, Date date, Time time) {
        String query = "INSERT INTO Appointments (appID , patientID, type, date, time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters
            stmt.setInt(1, appID);
            stmt.setLong(2,patientID);
            stmt.setString (3, type);
            stmt.setDate(4, date);
            stmt.setTime(5, time);
            

            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reservation is done  successfully!");
            } else {
                System.out.println("Reservation is failed!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
