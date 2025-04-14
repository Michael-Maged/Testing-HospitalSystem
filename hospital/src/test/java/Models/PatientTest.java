package Models;

import org.junit.jupiter.api.Test;

import com.hospital.Appointment;
import com.hospital.Bill;
import com.hospital.MedicalRecord;
import com.hospital.Patient;

import java.sql.Date;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PatientTest {

    @Test
    public void testConstructorAndGetters() {
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        

        assertEquals(1, patient.getPatientID());
        assertEquals("Alice", patient.getName());
        assertEquals(25, patient.getAge());
        assertEquals("Female", patient.getGender());
        assertEquals("123 Main St", patient.getAddress());
        assertEquals("555-1234", patient.getPhoneNumber());
        assertEquals(0, patient.getAppointments().size());
        assertEquals(0, patient.getBills().size());
    }

    @Test
    public void testSetters() {
        Patient patient = new Patient(2, "Bob", 30, "Male", "456 Elm St", "555-5678");
        Connection mockConnection = mock(Connection.class); // Mock a Connection object
        
        patient.setConn(mockConnection); 
        patient.setName("Bobby");
        patient.setAge(31);
        patient.setGender("Other");
        patient.setAddress("789 Oak St");
        patient.setPhoneNumber("555-0000");

        assertEquals("Bobby", patient.getName());
        assertEquals(31, patient.getAge());
        assertEquals("Other", patient.getGender());
        assertEquals("789 Oak St", patient.getAddress());
        assertEquals("555-0000", patient.getPhoneNumber());
        assertEquals(mockConnection, patient.getConn());
    }


    @Test
    public void testAddAppointment() {
        Patient patient = new Patient(3, "Charlie", 40, "Male", "999 Maple Ave", "555-7890");
    
        Appointment app = new Appointment(10, 3, "Checkup",
                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 200);
    
        patient.addAppointment(app);
        ArrayList<Appointment> appointments = patient.getAppointments();
    
        assertEquals(1, appointments.size()); // After adding 1 appointment, size should be 1
        assertEquals(app, appointments.get(0)); // Ensure the first appointment is the one added
    
        // Now adding a second appointment
        Appointment appointment = new Appointment(201, 1, "Checkup", new java.util.Date(), new java.sql.Time(System.currentTimeMillis()), 501);
        patient.addAppointment(appointment);
        
        // Now, the appointments list should have 2 appointments
        assertEquals(2, patient.getAppointments().size()); 
        assertEquals(appointment.getAppID(), patient.getAppointments().get(1).getAppID()); // Check second appointment
    }
    
    @Test
    public void testAddBill() {
        Patient patient = new Patient(4, "Dana", 35, "Female", "234 Cedar Rd", "555-4444");

        Bill bill = new Bill(101,200, 250.0, new Date(System.currentTimeMillis()));

        patient.addBill(bill);
        ArrayList<Bill> bills = patient.getBills();

        assertEquals(1, bills.size());
        assertEquals(bill, bills.get(0));
    }
 @Test
    public void testAddMedicalRecord() {
        Patient patient = new Patient(5, "Eva", 28, "Female", "12 River St", "555-6789");

        MedicalRecord record = new MedicalRecord(101, patient.getPatientID(), "Flu", "Rest and hydration", new Date(System.currentTimeMillis()));

        patient.addrecord(record);
        ArrayList<MedicalRecord> records = patient.getMedicalRecords();

        assertEquals(1, records.size());
        assertEquals(record, records.get(0)); // Ensure the added record is present
    }

    @Test
    public void testToString() {
        Patient patient = new Patient(5, "Eva", 28, "Female", "12 River St", "555-6789");

        Appointment app = new Appointment(20, 5, "Consultation",
                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 101);
        patient.addAppointment(app);

        Bill bill = new Bill(101,200, 250.0, new Date(System.currentTimeMillis()));
        patient.addBill(bill);

        String result = patient.toString();

        assertTrue(result.contains("Patient ID: 5"));
        assertTrue(result.contains("Name: Eva"));
        assertTrue(result.contains("Appointments:"));
        assertTrue(result.contains("Bills:"));
    }

        @Test
    public void testFetchUserAppointments() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Set up the mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Simulate one appointment in the result set
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One row, then end
        when(mockResultSet.getInt("appID")).thenReturn(1);
        when(mockResultSet.getInt("docID")).thenReturn(101);
        when(mockResultSet.getString("type")).thenReturn("Checkup");
        when(mockResultSet.getDate("date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(mockResultSet.getTime("time")).thenReturn(new java.sql.Time(System.currentTimeMillis()));
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method
        ArrayList<Appointment> appointments = patient.FetchUserAppointments();
    
        // Verify the results
        assertEquals(1, appointments.size());
        Appointment appointment = appointments.get(0);
        assertEquals(1, appointment.getAppID());
        assertEquals(101, appointment.getDocID());
        assertEquals("Checkup", appointment.getType());
        assertNotNull(appointment.getDate());
        assertNotNull(appointment.getTime());
    
        // Verify interactions with the mock
        verify(mockPreparedStatement, times(1)).setInt(1, patient.getPatientID());
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testFetchUserRecords() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Set up the mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Simulate one medical record in the result set
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One row, then end
        when(mockResultSet.getInt("recordID")).thenReturn(1);
        when(mockResultSet.getInt("patientID")).thenReturn(1);
        when(mockResultSet.getString("diagnosis")).thenReturn("Flu");
        when(mockResultSet.getString("treatment")).thenReturn("Rest and hydration");
        when(mockResultSet.getDate("date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method
        ArrayList<MedicalRecord> records = patient.FetchUserRecords();
    
        // Verify the results
        assertEquals(1, records.size());
        MedicalRecord record = records.get(0);
        assertEquals(1, record.getRecordID());
        assertEquals(1, record.getPatientID());
        assertEquals("Flu", record.getDiagnosis());
        assertEquals("Rest and hydration", record.getTreatment());
        assertNotNull(record.getDate());
    
        // Verify interactions with the mock
        verify(mockPreparedStatement, times(1)).setInt(1, patient.getPatientID());
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testFetchUserAppointmentsCatchBlock() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
    
        // Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method and verify that it handles the exception
        ArrayList<Appointment> appointments = patient.FetchUserAppointments();
        assertEquals(0, appointments.size()); // Should return an empty list
    }

    @Test
    public void testFetchUserAppointmentsElseBlock() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Set up the mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Simulate no rows in the result set
        when(mockResultSet.next()).thenReturn(false); // No rows
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method and verify the results
        ArrayList<Appointment> appointments = patient.FetchUserAppointments();
        assertEquals(0, appointments.size()); // Should return an empty list
    }

    @Test
    public void testFetchUserRecordsCatchBlock() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
    
        // Simulate an exception when preparing the statement
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method and verify that it handles the exception
        ArrayList<MedicalRecord> records = patient.FetchUserRecords();
        assertEquals(0, records.size()); // Should return an empty list
    }

    @Test
    public void testFetchUserRecordsElseBlock() throws Exception {
        // Mock the connection and prepared statement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Set up the mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Simulate no rows in the result set
        when(mockResultSet.next()).thenReturn(false); // No rows
    
        // Create a Patient instance and set the mocked connection
        Patient patient = new Patient(1, "Alice", 25, "Female", "123 Main St", "555-1234");
        patient.setConn(mockConnection);
    
        // Call the method and verify the results
        ArrayList<MedicalRecord> records = patient.FetchUserRecords();
        assertEquals(0, records.size()); // Should return an empty list
    }


}
