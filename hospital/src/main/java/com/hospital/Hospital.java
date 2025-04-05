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

    public Patient loginPatient(String phone, int patientId){
        String query = "SELECT * FROM patients WHERE phone = ? AND patientID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, phone);
            stmt.setInt(2, patientId);

            // Execute the query and get the result
            ResultSet rs = stmt.executeQuery();

            // Check if a matching patient was found
            if (rs.next()) {
                // Create a new Patient object and populate it with the data from the result set
                String patientID = rs.getString("patientID");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone");

                // Return the populated Patient object
                return new Patient(patientID, name, age, gender, address, phoneNumber);
            } else {
                // No matching patient found
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Return null in case of error
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
<<<<<<< HEAD
    public void addMedicalRecord(int recordID, long patientID, String diagnosis , String treatment) {
        String query = "INSERT INTO MedicalRecords (recordID , patientID, diagnosis, treatment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters
            stmt.setInt(1, recordID);
            stmt.setLong(2,patientID);
            stmt.setString (3, diagnosis);
            stmt.setString(4,treatment);
            
            

            // Execute the query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(" Medical record has been added successfully!");
            } else {
                System.out.println(" Failed to add the medical record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void scheduleAppointment(Appointment a) {
        appointments.add(a);
        System.out.println("Scheduled appointment for patient ID " + a.getPatientID());
    }
=======
>>>>>>> 3090e6cef238717a844220f0c0d4e98a340f2f35

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
