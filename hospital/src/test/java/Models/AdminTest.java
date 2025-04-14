package Models;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hospital.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AdminTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    private AutoCloseable mocks;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        admin = new Admin(mockConnection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testAddDoctor() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insertion

        // Call the method
        admin.addDoctor(1, "Dr. John", 45, "Male", "Cardiology");

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, "Dr. John");
        verify(mockPreparedStatement, times(1)).setInt(3, 45);
        verify(mockPreparedStatement, times(1)).setString(4, "Male");
        verify(mockPreparedStatement, times(1)).setString(5, "Cardiology");
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful deletion

        // Call the method
        admin.deleteDoctor(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddInventoryItem() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insertion

        // Call the method
        admin.addInventoryItem(1, "Syringe", 100);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setString(2, "Syringe");
        verify(mockPreparedStatement, times(1)).setInt(3, 100);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteInventoryItem() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful deletion

        // Call the method
        admin.deleteInventoryItem(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insertion

        // Call the method
        admin.addMedicalRecord(1, 101, "Flu", "Rest and hydration", new java.sql.Date(System.currentTimeMillis()));

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).setInt(2, 101);
        verify(mockPreparedStatement, times(1)).setString(3, "Flu");
        verify(mockPreparedStatement, times(1)).setString(4, "Rest and hydration");
        verify(mockPreparedStatement, times(1)).setDate(eq(5), any(java.sql.Date.class));
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful deletion

        // Call the method
        admin.deleteMedicalRecord(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddBill() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insertion

        // Call the method
        admin.addBill(101, 500.0, new java.sql.Date(System.currentTimeMillis()));

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 101);
        verify(mockPreparedStatement, times(1)).setDouble(2, 500.0);
        verify(mockPreparedStatement, times(1)).setDate(eq(3), any(java.sql.Date.class));
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteBill() throws Exception {
        // Mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful deletion

        // Call the method
        admin.deleteBill(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

        @Test
    public void testAdminConstructor() {
        // Create an Admin instance
        Admin admin = new Admin();

        // Verify that the connection is established
        Connection connection = admin.conn;
        assertNotNull(connection, "Connection should not be null");

        // Verify that the connection is valid
        try {
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (Exception e) {
            fail("Connection validation threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddDoctorElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.addDoctor(1, "Dr. John", 45, "Male", "Cardiology");

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteDoctorElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.deleteDoctor(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddDoctorCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.addDoctor(1, "Dr. John", 45, "Male", "Cardiology");

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testDeleteDoctorCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.deleteDoctor(1);

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testAddBillCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.addBill(101, 500.0, new java.sql.Date(System.currentTimeMillis()));

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testDeleteBillCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.deleteBill(1);

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testAddBillElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.addBill(101, 500.0, new java.sql.Date(System.currentTimeMillis()));

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteBillElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.deleteBill(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
public void testAddMedicalRecordCatchBlock() throws Exception {
    // Mock behavior: Simulate an exception when preparing the statement
    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

    // Call the method
    admin.addMedicalRecord(1, 101, "Flu", "Rest and hydration", new java.sql.Date(System.currentTimeMillis()));

    // Verify that the exception is caught and handled
    verify(mockConnection, times(1)).prepareStatement(anyString());
}

    @Test
    public void testDeleteMedicalRecordCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.deleteMedicalRecord(1);

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testAddMedicalRecordElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.addMedicalRecord(1, 101, "Flu", "Rest and hydration", new java.sql.Date(System.currentTimeMillis()));

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteMedicalRecordElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.deleteMedicalRecord(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }


    @Test
    public void testAddInventoryItemCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.addInventoryItem(1, "Syringe", 100);

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }
    @Test
    public void testDeleteInventoryItemCatchBlock() throws Exception {
        // Mock behavior: Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Call the method
        admin.deleteInventoryItem(1);

        // Verify that the exception is caught and handled
        verify(mockConnection, times(1)).prepareStatement(anyString());
    }
    @Test
    public void testAddInventoryItemElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.addInventoryItem(1, "Syringe", 100);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
    @Test
    public void testDeleteInventoryItemElseBranch() throws Exception {
        // Mock behavior: Simulate no rows affected
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        // Call the method
        admin.deleteInventoryItem(1);

        // Verify the query execution
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
    
}