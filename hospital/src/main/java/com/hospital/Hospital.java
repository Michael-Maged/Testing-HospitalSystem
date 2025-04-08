package com.hospital;
import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Hospital {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
    private static final String USER = "testing";
    private static final String PASSWORD = "mypass";

    List<Patient> patients = new ArrayList<>();
    List<Appointment> appointments = new ArrayList<>();
    List<MedicalRecord> records = new ArrayList<>();
    List<InventoryItem> inventory = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();

    // --- Inventory ---

    public void fetchInventoryItems() {
        inventory.clear();
        String query = "SELECT * FROM Inventory";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
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

    public void fetchMedicalRecords(int patientID) {
        records.clear();
        String query = "SELECT * FROM MedicalRecords WHERE patientID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, patientID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int recordID = rs.getInt("recordID");
                String diagnosis = rs.getString("diagnosis");
                String treatment = rs.getString("treatment");
                Date date = rs.getDate("Date");
                records.add(new MedicalRecord(recordID, patientID, diagnosis, treatment,date));
            }
            System.out.println("Fetched medical records for patient ID " + patientID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchDoctors() {
        doctors.clear();
        String query = "SELECT * FROM Doctors";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                int appID = rs.getInt("appID");
                int patientID = rs.getInt("patientID");
                String type = rs.getString("type");
                int Docid = rs.getInt("docID");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
    
                appointments.add(new Appointment(appID, patientID, type, date, time,Docid));
            }
            System.out.println("Fetched appointments from DB.");
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void registerPatient(String name, int age, String gender, String address, String phone)
    {  int newId = this.getNextPatientId();
       String query = "INSERT INTO Patients (patientID, name, age, gender, address, phone) VALUES (?, ?, ?, ?, ?, ?)";
       try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

           // Set parameters
           stmt.setInt(1, newId);  // Automatically generated ID
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

   public void loginPatient(String phone, String name){
       String query = "SELECT * FROM patients WHERE phone = ? AND name = ?";

       try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

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
           }

       } catch (SQLException e) {
           e.printStackTrace(); // Return null in case of error
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

   public void showPatients() {
       for (Patient p : patients) {
           p.toString();
       }
   }

   public int getNextPatientId() {
       int newId = 0;
       String query = "SELECT MAX(patientID) + 1 AS next_id FROM Patients";

       try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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

   public int getNextAppointmentId(String type) {
    int newId = 0;
    String query = "SELECT COALESCE(MAX(appID), 0) + 1 AS next_id FROM Appointments WHERE type = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Set the type parameter in the query
        pstmt.setString(1, type);
        
        // Execute the query and get the result
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                newId = rs.getInt("next_id");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Handle exception
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

   public <T> T findById(List<T> list, long id) {
    for (T item : list) {
        try {
            long itemId = (long) item.getClass().getMethod("getId").invoke(item);
            if (itemId == id) {
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return null;
}
}
