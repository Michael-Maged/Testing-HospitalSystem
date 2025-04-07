package com.hospital;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.sql.Time;

public class Patient {
    // Attributes
    private long patientID;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phoneNumber;
    private ArrayList<Appointment> appointments;
    private ArrayList<Bill> bills;

    // Constructor
    public Patient(long patientID, String name, int age, String gender, String address, String phoneNumber) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.appointments = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    // Getters
    public long getPatientID() { return patientID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public ArrayList<Bill> getBills() { return bills; }
    
    public ArrayList<Appointment> getAppointments() { 
        appointments.clear();

        // Query to get appointments for this patient
        String query = "SELECT appID, type, date, time FROM appointments WHERE patientID?";

        // Connect to the database and execute the query
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false", "johnnazizz", "blabla1");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the patient ID as the parameter for the query
            stmt.setLong(1, this.patientID);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and create Appointment objects
            while (rs.next()) {
                long appointmentId = rs.getInt("appID");
                long docID = rs.getInt("docID");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");

                // Create an Appointment object and add it to the list
                Appointment appointment = new Appointment(appointmentId, this.patientID, type, date, time, docID);
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of appointments
        return appointments;
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
