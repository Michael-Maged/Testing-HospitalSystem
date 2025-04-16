package Models;
import com.hospital.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; 
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.sql.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)  // JUnit 5 extension for Mockito
class patient_Test {

    Patient patient;

    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockStatement;
    @Mock private ResultSet mockResultSet;

    @BeforeEach
    void setup() {
        patient = new Patient(1, "Noha Elsayed", 20, "Female", "Golf ,villa 1133", "123-456");
        patient.setConn(mockConn);
    }

    @Test
    void TestConstructorInitialization() {
        assertEquals(1, patient.getPatientID());
        assertEquals("Noha Elsayed", patient.getName());
        assertEquals(20, patient.getAge());
        assertEquals("Female", patient.getGender());
        assertEquals("Golf ,Villa 1133", patient.getAddress());
        assertEquals("123-456", patient.getPhoneNumber());
        assertNotNull(patient.getAppointments());
        assertNotNull(patient.getMedicalRecords());
        assertNotNull(patient.getBills());
        assertNotNull(patient.getConn());
    }

    @Test
    void testConstructorHandlesSQLException() throws SQLException {
        // Mock the connection to throw an exception for DriverManager.getConnection
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("Database connection error"));
        
        // Now test the constructor by creating the patient instance
        Patient p = new Patient(1, "Farah Fady", 20, "Female", "123 Street", "555-1234");
        
        // Assert that the connection is null because of the exception
        assertNull(p.getConn());
    }

    @Test
    void TestSettersGetters() {
        patient.setName("Carol Moheb");
        patient.setAge(21);
        patient.setGender("Female");
        patient.setAddress("Heliopolis,Villa 24");
        patient.setPhoneNumber("500-321");

        assertEquals("Carol Moheb", patient.getName());
        assertEquals(21, patient.getAge());
        assertEquals("Female", patient.getGender());
        assertEquals("Heliopolis,Villa 24", patient.getAddress());
        assertEquals("500-321", patient.getPhoneNumber());
    }

    @Test
    void TestAddandGetAppt() {
        Appointment app = new Appointment(1, 1, "Checkup", new Date(0), new Time(0), 1);
        patient.addAppointment(app);
        assertTrue(patient.getAppointments().contains(app));
    }

    @Test
    void TestAddandGetBills() {
        Bill bill = new Bill(1, 1, 100.0, new Date(0));
        patient.addBill(bill);
        assertTrue(patient.getBills().contains(bill));
    }

    @Test
    void TestAddandGetRecords() {
        MedicalRecord record = new MedicalRecord(1, 1, "Flu", "Rest", new Date(0));
        patient.addrecord(record);
        assertTrue(patient.getMedicalRecords().contains(record));
    }

    @Test
    void testSetandGetConnection() {
        Connection originalConn = patient.getConn();
        patient.setConn(null);
        assertNull(patient.getConn());
        
        patient.setConn(originalConn);
        assertNotNull(patient.getConn());
    }

    @Test
    void TestToString() {
        String expected = "ID: 1, Name: Noha Elsayed, Age: 20, Gender: Female, Address: Golf ,villa 1133, Phone: 123-456";
        assertEquals(expected, patient.toString());
    }

    @Test
    void testFetchUserAppointments_successfulFetch() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One result
        when(mockResultSet.getInt("appID")).thenReturn(1);
        when(mockResultSet.getInt("docID")).thenReturn(101);
        when(mockResultSet.getString("type")).thenReturn("Checkup");
        when(mockResultSet.getDate("date")).thenReturn(Date.valueOf("2023-04-15"));
        when(mockResultSet.getTime("time")).thenReturn(Time.valueOf("10:30:00"));

        ArrayList<Appointment> appointments = patient.FetchUserAppointments();

        assertEquals(1, appointments.size());
        assertEquals("Checkup", appointments.get(0).getType());
    }

    @Test
    void testFetchUserAppointments_handlesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        ArrayList<Appointment> appointments = patient.FetchUserAppointments();

        assertTrue(appointments.isEmpty());
    }

    @Test
    void testFetchUserRecords_successfulFetch() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One result
        when(mockResultSet.getInt("recordID")).thenReturn(1);
        when(mockResultSet.getString("diagnosis")).thenReturn("Flu");
        when(mockResultSet.getDate("date")).thenReturn(Date.valueOf("2023-04-15"));

        ArrayList<MedicalRecord> records = patient.FetchUserRecords();

        assertEquals(1, records.size());
        assertEquals("Flu", records.get(0).getDiagnosis());
    }

    @Test
    void testFetchUserRecords_handlesSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        ArrayList<MedicalRecord> records = patient.FetchUserRecords();

        assertTrue(records.isEmpty());
    }
}
