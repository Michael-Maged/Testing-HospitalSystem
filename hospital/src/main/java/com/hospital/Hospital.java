package com.hospital;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Hospital {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
    private static final String USER = "testing";
    private static final String PASSWORD = "mypass";

    final List<String> departments = Arrays.asList(
            "Cardiology", // Heart-related health issues
            "Orthopedics", // Bone and joint problems
            "Pediatrics", // Child health
            "Neurology", // Brain and nervous system
            "Dermatology" // Skin-related issues
    );
    List<Patient> patients = new ArrayList<>();
    List<Appointment> appointments = new ArrayList<>();
    List<MedicalRecord> records = new ArrayList<>();
    List<InventoryItem> inventory = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();
    List<Bill> bills = new ArrayList<>();
    Connection conn;

    public Hospital() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hospital(Connection connection) {
        try {
            this.conn = connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Inventory ---

    public void fetchInventoryItems() {
        inventory.clear();
        String query = "SELECT * FROM Inventory";
        try (   Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int itemID = rs.getInt("itemID");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                inventory.add(new InventoryItem(itemID, name, quantity));
            }
            System.out.println("Fetched inventory items from DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchMedicalRecords() {
        records.clear();
        String query = "SELECT * FROM MedicalRecords";

        try (
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int recordID = rs.getInt("recordID");
                int patientID = rs.getInt("patientID");
                String diagnosis = rs.getString("diagnosis");
                String treatment = rs.getString("treatment");
                Date date = rs.getDate("Date");

                records.add(new MedicalRecord(recordID, patientID, diagnosis, treatment, date));
            }
            System.out.println("Fetched all medical records.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchDoctors() {
        doctors.clear();
        String query = "SELECT * FROM Doctors";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int doctorID = rs.getInt("docID");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String specialty = rs.getString("specialty");
                doctors.add(new Doctor(doctorID, name, age, gender, specialty));
            }
            System.out.println("Fetched doctors from DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchPatients() {
        patients.clear();
        String query = "SELECT * FROM Patients";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int patientID = rs.getInt("patientID");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                patients.add(new Patient(patientID, name, age, gender, address, phone));
            }
            System.out.println("Fetched patients from DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchAppointments() {
        appointments.clear();
        String query = "SELECT * FROM Appointments";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int appID = rs.getInt("appID");
                int patientID = rs.getInt("patientID");
                String type = rs.getString("type");
                int Docid = rs.getInt("docID");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");

                appointments.add(new Appointment(appID, patientID, type, date, time, Docid));
            }
            System.out.println("Fetched appointments from DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchBills() {
        bills.clear();
        String query = "SELECT * FROM Bills";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int billId = rs.getInt("billId");
                int patientID = rs.getInt("patientID");
                double amount = rs.getFloat("amount");
                Date date = rs.getDate("billDate");
                bills.add(new Bill(patientID,billId, amount, date));
            }
            System.out.println("Fetched Bills from DB.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerPatient(String name, int age, String gender, String address, String phone) {
        int newId = this.getNextPatientId();
        String query = "INSERT INTO Patients (patientID, name, age, gender, address, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setInt(1, newId); // Automatically generated ID
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

    public boolean phoneExists(String phoneNumber) {
        for (Patient d : patients) {
            System.out.println(d.toString());
            if (d.getPhoneNumber().equals(phoneNumber))
                return true;
        }
        return false;

    }

    public void loginPatient(String phone, String name) {
        String query = "SELECT * FROM patients WHERE phone = ? AND name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, phone);
            stmt.setString(2, name);

            // Execute the query and get the result
            ResultSet rs = stmt.executeQuery();

            // Check if a matching patient was found
            if (rs.next()) {
                // Create a new Patient object and populate it with the data from the result set
                int patientID = rs.getInt("patientID");
                String patientName = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone");

                // Return the populated Patient object
                Patient loggedInPatient = new Patient(patientID, patientName, age, gender, address, phoneNumber);
                Session.getInstance().setCurrentPatient(loggedInPatient);
            }else {
                Session.getInstance().setCurrentPatient(null); // No matching patient found 
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Return null in case of error
        }
    }

    public void scheduleAppointment(int appID, int patientID, String type, Date date, Time time, int docID) {
        String query = "INSERT INTO Appointments (appID , patientID, docID, type, date, time) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters
            stmt.setInt(1, appID);
            stmt.setInt(2, patientID);
            stmt.setInt(3, docID);
            stmt.setString(4, type);
            stmt.setDate(5, date);
            stmt.setTime(6, time);

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

    public void cancelAppointment(int appID) {
        String query = "DELETE FROM Appointments WHERE appID = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, appID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor deleted successfully!");
            } else {
                System.out.println("No doctor found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showPatients() {
        for (Patient p : patients) {
            System.out.println(p.toString());
        }
    }

    public int getNextPatientId() {
        int newId = 0;
        String query = "SELECT MAX(patientID) + 1 AS next_id FROM Patients";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                newId = rs.getInt("next_id");
                if (newId == 0) {
                    // If there are no patients, start from 1
                    newId = 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public int getNextDoctorId() {
        int newId = 0;
        String query = "SELECT MAX(docID) + 1 AS next_id FROM Doctors";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                newId = rs.getInt("next_id");
                if (newId == 0) {
                    newId = 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public int getNextInventoryId() {
        int newId = 0;
        String query = "SELECT MAX(itemID) + 1 AS next_id FROM Inventory";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                newId = rs.getInt("next_id");
                if (newId == 0) {
                    newId = 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public int getNextAppointmentId() {
        int newId = 1; // Start from 1 by default
        String query = "SELECT MAX(appID) AS max_id FROM Appointments";

        try (
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                if (!rs.wasNull()) {
                    newId = maxId + 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Better to log this in production
        }

        return newId;
    }

    public int getNextRecordId() {
        int newId = 0;
        String query = "SELECT MAX(recordID) + 1 AS next_id FROM MedicalRecords";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                newId = rs.getInt("next_id");
                if (newId == 0) {
                    newId = 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public int getNextBillId() {
        int newId = 0;
        String query = "SELECT MAX(billId) + 1 AS next_id FROM Bills";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                newId = rs.getInt("next_id");
                if (newId == 0) {
                    newId = 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<MedicalRecord> getRecords() {
        return records;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public <T> T findById(List<T> list, int id) {
        for (T item : list) {
            try {
                int itemId = (int) item.getClass().getMethod("getId").invoke(item);
                if (itemId == id) {
                    return item;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (name.equals(doctor.getName())) {
                return doctor;
            }
        }
        System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" + doctors.size());
        return null;
    }

    public Patient findPatientById(int patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID() == patientID) {
                return patient;
            }
        }
        return null; // Return null if no patient with the given ID is found
    }
    public Doctor findDoctorById(int doctorID) {
        for (Doctor dr : doctors) {
            if (dr.getDoctorID() == doctorID) {
                return dr;
            }
        }
        return null; // Return null if no doctor with the given ID is found
    }

    public void setConnection(Connection connection) {
        this.conn = connection;
    }
    

}
