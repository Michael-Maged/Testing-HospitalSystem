package com.hospital;
import java.sql.*;

public class Admin {
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
    private static final String USER = "testing";
    private static final String PASSWORD = "mypass";
    
    public void addDoctor(int docID, String name, int age, String gender, String specialty) {
        String query = "INSERT INTO Doctors (docID, name, age, gender, specialty) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, docID);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setString(4, gender);
            stmt.setString(5, specialty);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor added successfully!");
            } else {
                System.out.println("Failed to add doctor.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Doctor
    public void deleteDoctor(int docID) {
        String query = "DELETE FROM Doctors WHERE docID = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, docID);
            
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

    // Add Inventory Item
    public void addInventoryItem(int itemID, String name, int quantity) {
        String query = "INSERT INTO Inventory (itemID, name, quantity) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, itemID);
            stmt.setString(2, name);
            stmt.setInt(3, quantity);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory item added successfully!");
            } else {
                System.out.println("Failed to add inventory item.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Inventory Item
    public void deleteInventoryItem(int itemID) {
        String query = "DELETE FROM Inventory WHERE itemID = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, itemID);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory item deleted successfully!");
            } else {
                System.out.println("No inventory item found with the given ID.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Medical Record
    public void addMedicalRecord(int recordID, int patientID, String diagnosis, String treatment , Date date) {
        String query = "INSERT INTO MedicalRecords (recordID, patientID, diagnosis, treatment) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, recordID);
            stmt.setInt(2, patientID);
            stmt.setString(3, diagnosis);
            stmt.setString(4, treatment);
            stmt.setDate(5, date);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medical record added successfully!");
            } else {
                System.out.println("Failed to add medical record.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Medical Record
    public void deleteMedicalRecord(int recordID) {
        String query = "DELETE FROM MedicalRecords WHERE recordID = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, recordID);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medical record deleted successfully!");
            } else {
                System.out.println("No medical record found with the given ID.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // Add Bill
    public void addBill(int patientID, double amount, Date billDate) {
        String query = "INSERT INTO Bills (patientID, amount, billDate) VALUES (?,?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);
            stmt.setDouble(2, amount);
            stmt.setDate(3, billDate);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bill added successfully!");
            } else {
                System.out.println("Failed to add bill.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Delete Bill
    public void deleteBill(int billId) {
        String query = "DELETE FROM Bills WHERE billId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, billId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bill deleted successfully!");
            } else {
                System.out.println("No bill found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
