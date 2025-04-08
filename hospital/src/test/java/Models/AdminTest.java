package Models;
package com.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;

import static org.mockito.Mockito.*;

public class AdminTest {

    @Mock
    private Connection mockConn;
    
    @Mock
    private PreparedStatement mockStmt;

    @Mock
    private ResultSet mockResultSet;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
    }

    // Test for addDoctor method
    @Test
    public void testAddDoctor() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful insert

        // Act
        admin.addDoctor(1, "Dr. Smith", 45, "Male", "Cardiology");

        // Assert
        verify(mockStmt, times(1)).setLong(1, 1);
        verify(mockStmt, times(1)).setString(2, "Dr. Smith");
        verify(mockStmt, times(1)).setInt(3, 45);
        verify(mockStmt, times(1)).setString(4, "Male");
        verify(mockStmt, times(1)).setString(5, "Cardiology");
        verify(mockStmt, times(1)).executeUpdate();
    }

    // Test for deleteDoctor method
    @Test
    public void testDeleteDoctor() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful deletion

        // Act
        admin.deleteDoctor(1);

        // Assert
        verify(mockStmt, times(1)).setLong(1, 1);
        verify(mockStmt, times(1)).executeUpdate();
    }

    // Test for addInventoryItem method
    @Test
    public void testAddInventoryItem() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful insert

        // Act
        admin.addInventoryItem(101, "Syringe", 50);

        // Assert
        verify(mockStmt, times(1)).setLong(1, 101);
        verify(mockStmt, times(1)).setString(2, "Syringe");
        verify(mockStmt, times(1)).setInt(3, 50);
        verify(mockStmt, times(1)).executeUpdate();
    }

    // Test for deleteInventoryItem method
    @Test
    public void testDeleteInventoryItem() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful deletion

        // Act
        admin.deleteInventoryItem(101);

        // Assert
        verify(mockStmt, times(1)).setInt(1, 101);
        verify(mockStmt, times(1)).executeUpdate();
    }

    // Test for addMedicalRecord method
    @Test
    public void testAddMedicalRecord() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful insert

        Date date = new Date(System.currentTimeMillis());

        // Act
        admin.addMedicalRecord(1001, 123, "Flu", "Rest and medication", date);

        // Assert
        verify(mockStmt, times(1)).setLong(1, 1001);
        verify(mockStmt, times(1)).setLong(2, 123);
        verify(mockStmt, times(1)).setString(3, "Flu");
        verify(mockStmt, times(1)).setString(4, "Rest and medication");
        verify(mockStmt, times(1)).setDate(5, date);
        verify(mockStmt, times(1)).executeUpdate();
    }

    // Test for deleteMedicalRecord method
    @Test
    public void testDeleteMedicalRecord() throws SQLException {
        // Arrange
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1);  // Simulate a successful deletion

        // Act
        admin.deleteMedicalRecord(1001);

        // Assert
        verify(mockStmt, times(1)).setLong(1, 1001);
        verify(mockStmt, times(1)).executeUpdate();
    }
}

