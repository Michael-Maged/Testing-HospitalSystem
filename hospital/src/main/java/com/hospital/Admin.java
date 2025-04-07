package com.hospital;

import java.sql.*;

public class Admin {
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
    private static final String USER = "testing";
    private static final String PASSWORD = "mypass";
    
    // Method to add a new doctor to the database
    public void addDoctor(long docID, String name, int age, String gender, String specialty) {
        String query = "INSERT INTO Doctors (docID, name, age, gender, specialty) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, docID);
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

    // Method to add a new inventory item to the database
    public void addInventoryItem(int itemID, String name, int quantity) {
        String query = "INSERT INTO InventoryItems (itemID, name, quantity) VALUES (?, ?, ?)";
        
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

    // Method to add a new medical record to the database
    public void addMedicalRecord(long recordID, long patientID, String diagnosis, String treatment) {
        String query = "INSERT INTO MedicalRecords (recordID, patientID, diagnosis, treatment) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, recordID);
            stmt.setLong(2, patientID);
            stmt.setString(3, diagnosis);
            stmt.setString(4, treatment);
            
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
}
