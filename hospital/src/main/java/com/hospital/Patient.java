package com.hospital;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Time;

public class Patient {
    // Attributes
    private int patientID;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phoneNumber;
    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalRecord> records;
    private ArrayList<Bill> bills;

    // Constructor
    public Patient(int patientID, String name, int age, String gender, String address, String phoneNumber) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.appointments = new ArrayList<>();
        this.bills = new ArrayList<>();
        this.records = new ArrayList<>();

    }

    // Getters
    public int getPatientID() { return patientID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public ArrayList<Bill> getBills() { return bills; }
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public ArrayList<MedicalRecord> getMedicalRecords() { return records; }
    
    public ArrayList<Appointment> FetchUserAppointments() { 
        appointments.clear();

        // Query to get appointments for this patient
        String query = "SELECT appID, docID , type, date, time FROM Appointments WHERE patientID = ?";

        // Connect to the database and execute the query
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false", "testing", "mypass");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the patient ID as the parameter for the query
            stmt.setInt(1, this.patientID);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and create Appointment objects
            while (rs.next()) {
                int appointmentId = rs.getInt("appID");
                int docID = rs.getInt("docID");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");

                // Create an Appointment object and add it to the list
                Appointment appointment = new Appointment(appointmentId, this.patientID, type, date, time, docID);
                System.out.println(appointment.toString());
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of appointments
        return appointments;
    }



    public ArrayList<MedicalRecord> FetchUserRecords() {
        records.clear();

        String query = "SELECT * FROM MedicalRecords WHERE patientID = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false", "testing", "mypass");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the patientID parameter
            stmt.setInt(1, this.patientID);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int recordID = rs.getInt("recordID");
                int patientID = rs.getInt("patientID");
                String diagnosis = rs.getString("diagnosis");
                String treatment = rs.getString("treatment");
                Date recordDate = rs.getDate("recordDate"); // Assuming this is a Date column

                // Create a new MedicalRecord and add it to the list
                MedicalRecord record = new MedicalRecord(recordID, patientID, diagnosis, treatment, recordDate);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public void addrecord(MedicalRecord record) {
        records.add(record);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Patient ID: ").append(patientID).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Age: ").append(age).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Phone Number: ").append(phoneNumber).append("\n");

        sb.append("\nAppointments:\n");
        for (Appointment a : appointments) {
            sb.append(a.toString()).append("\n");
        }

        sb.append("\nBills:\n");
        for (Bill b : bills) {
            sb.append(b.toString()).append("\n");
        }

        return sb.toString();
    }
}
