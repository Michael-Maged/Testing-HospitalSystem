package Models;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hospital.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HospitalTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private AutoCloseable mocks; // For closing mocks after each test
    private Hospital hospital;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and the Hospital instance
        mockConnection = mock(Connection.class);
        mocks = MockitoAnnotations.openMocks(this);
        hospital = new Hospital();

        // Reset mocks to ensure a clean state before each test
        reset(mockConnection, mockPreparedStatement, mockResultSet);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Close mocks to avoid memory leaks
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testFetchDoctors() throws Exception {
        // Mock database behavior
        when(mockConnection.createStatement()).thenReturn(mock(Statement.class));
        when(mockConnection.createStatement().executeQuery("SELECT * FROM Doctors")).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one doctor
        when(mockResultSet.getInt("docID")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Dr. John");
        when(mockResultSet.getInt("age")).thenReturn(45);
        when(mockResultSet.getString("gender")).thenReturn("Male");
        when(mockResultSet.getString("specialty")).thenReturn("Cardiology");

        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchDoctors();

        // Verify the results
        List<Doctor> doctors = hospital.getDoctors();
        assertEquals(1, doctors.size());
        assertEquals("Dr. John", doctors.get(0).getName());
        assertEquals(45, doctors.get(0).getAge());
        assertEquals("Male", doctors.get(0).getGender());
        assertEquals("Cardiology", doctors.get(0).getSpecialty());
    }

    @Test
    public void testFetchPatients() throws Exception {
        // Mock database behavior
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM Patients")).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one patient
        when(mockResultSet.getInt("patientID")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getInt("age")).thenReturn(30);
        when(mockResultSet.getString("gender")).thenReturn("Male");
        when(mockResultSet.getString("address")).thenReturn("123 Street");
        when(mockResultSet.getString("phone")).thenReturn("555-1234");

        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchPatients();

        // Verify the results
        List<Patient> patients = hospital.getPatients();
        assertEquals(1, patients.size());
        assertEquals("John Doe", patients.get(0).getName());
        assertEquals(30, patients.get(0).getAge());
        assertEquals("Male", patients.get(0).getGender());
        assertEquals("123 Street", patients.get(0).getAddress());
        assertEquals("555-1234", patients.get(0).getPhoneNumber());
    }

    @Test
    public void testGetNextPatientId() throws Exception {
        hospital = new Hospital(mockConnection);
        // Mock database behavior
        when(mockConnection.createStatement()).thenReturn(mock(Statement.class));
        when(mockConnection.createStatement().executeQuery("SELECT MAX(patientID) + 1 AS next_id FROM Patients"))
                .thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("next_id")).thenReturn(5);

        // Call the method
        int nextId = hospital.getNextPatientId();

        // Verify the result
        assertEquals(5, nextId);
    }

}