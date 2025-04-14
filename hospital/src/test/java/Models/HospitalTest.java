package Models;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hospital.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void testFetchInventoryItems() throws Exception {
        // Mock database behavior
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM Inventory")).thenReturn(mockResultSet);
    
        // Simulate one inventory item being fetched
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one item
        when(mockResultSet.getInt("itemID")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Bandages");
        when(mockResultSet.getInt("quantity")).thenReturn(50);
    
        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchInventoryItems();
    
        // Verify the results
        List<InventoryItem> inventoryItems = hospital.getInventory();
        assertEquals(1, inventoryItems.size());
        assertEquals("Bandages", inventoryItems.get(0).getName());
        assertEquals(50, inventoryItems.get(0).getQuantity());
    }
    @Test
    public void testFetchMedicalRecords() throws Exception {
        // Mock database behavior
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement("SELECT * FROM MedicalRecords")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Simulate one medical record being fetched
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulate one record
        when(mockResultSet.getInt("recordID")).thenReturn(1);
        when(mockResultSet.getInt("patientID")).thenReturn(101);
        when(mockResultSet.getString("diagnosis")).thenReturn("Flu");
        when(mockResultSet.getString("treatment")).thenReturn("Rest and fluids");
        when(mockResultSet.getDate("Date")).thenReturn(Date.valueOf("2025-04-14"));
    
        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchMedicalRecords();
     
        // Verify the results
        List<MedicalRecord> medicalRecords = hospital.getRecords();
        assertEquals(1, medicalRecords.size());
        assertEquals(1, medicalRecords.get(0).getRecordID());
        assertEquals(101, medicalRecords.get(0).getPatientID());
        assertEquals("Flu", medicalRecords.get(0).getDiagnosis());
        assertEquals("Rest and fluids", medicalRecords.get(0).getTreatment());
        assertEquals(Date.valueOf("2025-04-14"), medicalRecords.get(0).getDate());
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
    public void testFetchAppointments() throws Exception {
        // Mock database behavior
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM Appointments")).thenReturn(mockResultSet);
    
        // Simulate one appointment record being fetched
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulate one record
        when(mockResultSet.getInt("appID")).thenReturn(1);
        when(mockResultSet.getInt("patientID")).thenReturn(101);
        when(mockResultSet.getString("type")).thenReturn("Consultation");
        when(mockResultSet.getInt("docID")).thenReturn(202);
        when(mockResultSet.getDate("date")).thenReturn(Date.valueOf("2025-04-14"));
        when(mockResultSet.getTime("time")).thenReturn(Time.valueOf("10:00:00"));
    
        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchAppointments();
    
        // Verify the results
        List<Appointment> appointmentsList = hospital.getAppointments();
        assertEquals(1, appointmentsList.size());
        assertEquals(1, appointmentsList.get(0).getAppID());
        assertEquals(101, appointmentsList.get(0).getPatientID());
        assertEquals("Consultation", appointmentsList.get(0).getType());
        assertEquals(202, appointmentsList.get(0).getDocID());
        assertEquals(Date.valueOf("2025-04-14"), appointmentsList.get(0).getDate());
        assertEquals(Time.valueOf("10:00:00"), appointmentsList.get(0).getTime());
    }
    @Test
    public void testFetchBills() throws Exception {
        // Mock database behavior
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM Bills")).thenReturn(mockResultSet);
    
        // Simulate one bill record being fetched
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulate one record
        when(mockResultSet.getInt("billId")).thenReturn(1);
        when(mockResultSet.getInt("patientID")).thenReturn(101);
        when(mockResultSet.getFloat("amount")).thenReturn(250.75f);
        when(mockResultSet.getDate("billDate")).thenReturn(Date.valueOf("2025-04-14"));
    
        hospital = new Hospital(mockConnection);
        // Call the method
        hospital.fetchBills();
    
        // Verify the results
        List<Bill> billsList = hospital.getBills();
        assertEquals(1, billsList.size());
        assertEquals(1, billsList.get(0).getBillId());
        assertEquals(101, billsList.get(0).getPatientID());
        assertEquals(250.75, billsList.get(0).getAmount(), 0.01);  // Allowing a small margin of error for floating point comparison
        assertEquals(Date.valueOf("2025-04-14"), billsList.get(0).getBillingDate());
    }
    @Test
    public void testRegisterPatient_Success() throws Exception {
        // Mock database behavior
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Mock the behavior of getNextPatientId() method (which likely uses createStatement)
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulating query result
    
        // Mock behavior for the PreparedStatement for insert
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful insert
    
        hospital = new Hospital(mockConnection);  // Use mocked connection
    
        // Call the method
        hospital.registerPatient("John Doe", 30, "Male", "123 Street", "555-1234");
    
        // Verify that the executeUpdate was called once
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
    
    @Test
    public void testRegisterPatient_Failure() throws Exception {
        // Mock database behavior
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
    
        // Mock the behavior of getNextPatientId() method (which likely uses createStatement)
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // Simulating query result
    
        // Mock behavior for the PreparedStatement for insert
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);  // Simulate failure (no rows affected)
    
        hospital = new Hospital(mockConnection);  // Use mocked connection
    
        // Call the method
        hospital.registerPatient("John Doe", 30, "Male", "123 Street", "555-1234");
    
        // Verify that the executeUpdate was called once
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
    @Test
public void testPhoneExists() {
    // Mock data: Create some sample patients
    Patient patient1 = new Patient(1, "John Doe", 30, "Male", "123 Street", "555-1234");
    Patient patient2 = new Patient(2, "Jane Doe", 25, "Female", "456 Avenue", "555-5678");

    // Create a Hospital object and add patients to the list
    hospital = new Hospital(mockConnection);
    hospital.getPatients().add(patient1);
    hospital.getPatients().add(patient2);

    // Test if a phone number exists
    boolean exists = hospital.phoneExists("555-1234");
    assertTrue(exists);  // Should return true as "555-1234" exists

    // Test if a phone number does not exist
    exists = hospital.phoneExists("555-0000");
    assertFalse(exists);  // Should return false as "555-0000" does not exist
}
@Test
public void testLoginPatient_Success() throws Exception {
    // Setup: create a mock result set that returns a patient
    when(mockConnection.prepareStatement("SELECT * FROM patients WHERE phone = ? AND name = ?")).thenReturn(mockPreparedStatement);
    
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
    when(mockResultSet.next()).thenReturn(true);  // Found patient
    when(mockResultSet.getInt("patientID")).thenReturn(1);
    when(mockResultSet.getString("name")).thenReturn("John Doe");
    when(mockResultSet.getInt("age")).thenReturn(30);
    when(mockResultSet.getString("gender")).thenReturn("Male");
    when(mockResultSet.getString("address")).thenReturn("123 Street");
    when(mockResultSet.getString("phone")).thenReturn("555-1234");

    hospital = new Hospital(mockConnection);
    hospital.loginPatient("555-1234", "John Doe");

    // Verify session was set correctly
    Patient currentPatient = Session.getInstance().getCurrentPatient();
    assertNotNull(currentPatient);
    assertEquals("John Doe", currentPatient.getName());
    assertEquals("555-1234", currentPatient.getPhoneNumber());
    assertEquals(30, currentPatient.getAge());
}
@Test
public void testLoginPatient_Failure() throws Exception {
    when(mockConnection.prepareStatement("SELECT * FROM patients WHERE phone = ? AND name = ?")).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(false); // No patient found

    hospital = new Hospital(mockConnection);
    hospital.loginPatient("000-0000", "Fake Name");

    // Expecting no patient to be set in session
    Patient currentPatient = Session.getInstance().getCurrentPatient();
    assertNull(currentPatient);
}
@Test
public void testScheduleAppointment_Success() throws Exception {
    // Arrange
    when(mockConnection.prepareStatement("INSERT INTO Appointments (appID , patientID, docID, type, date, time) VALUES (?, ?, ?, ?, ?, ?)"))
            .thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insert

    hospital = new Hospital(mockConnection);

    // Act
    int appID = 1;
    int patientID = 101;
    String type = "Checkup";
    Date date = Date.valueOf("2025-05-01");
    Time time = Time.valueOf("10:30:00");
    int docID = 201;

    hospital.scheduleAppointment(appID, patientID, type, date, time, docID);

    // Assert
    verify(mockPreparedStatement).setInt(1, appID);
    verify(mockPreparedStatement).setInt(2, patientID);
    verify(mockPreparedStatement).setInt(3, docID);
    verify(mockPreparedStatement).setString(4, type);
    verify(mockPreparedStatement).setDate(5, date);
    verify(mockPreparedStatement).setTime(6, time);
    verify(mockPreparedStatement).executeUpdate();
}
@Test
public void testScheduleAppointment_Failure() throws Exception {
    when(mockConnection.prepareStatement("INSERT INTO Appointments (appID , patientID, docID, type, date, time) VALUES (?, ?, ?, ?, ?, ?)"))
            .thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Simulate failure

    hospital = new Hospital(mockConnection);
    hospital.scheduleAppointment(1, 101, "Checkup", Date.valueOf("2025-05-01"), Time.valueOf("10:30:00"), 201);

    // You can verify that executeUpdate was called
    verify(mockPreparedStatement).executeUpdate();
}
@Test
public void testCancelAppointment_Success() throws Exception {
    when(mockConnection.prepareStatement("DELETE FROM Appointments WHERE appID = ?"))
            .thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate success

    hospital = new Hospital(mockConnection);
    hospital.cancelAppointment(1);

    verify(mockPreparedStatement).setInt(1, 1);
    verify(mockPreparedStatement).executeUpdate();
}
@Test
public void testCancelAppointment_Failure() throws Exception {
    when(mockConnection.prepareStatement("DELETE FROM Appointments WHERE appID = ?"))
            .thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

    hospital = new Hospital(mockConnection);
    hospital.cancelAppointment(999); // Non-existing appID

    verify(mockPreparedStatement).setInt(1, 999);
    verify(mockPreparedStatement).executeUpdate();
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


@Test
public void testGetNextDoctorId() throws Exception {
    // Mocks
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(docID) + 1 AS next_id FROM Doctors")).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(5); // simulate MAX(docID) = 4

    // Create hospital instance
    hospital = new Hospital(mockConnection);

    // Run method
    int nextId = hospital.getNextDoctorId();

    // Assert
    assertEquals(5, nextId);
}
@Test
public void testGetNextInventoryId() throws Exception {
    // Mocks
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(itemID) + 1 AS next_id FROM Inventory")).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(10); // simulate MAX(itemID) = 9

    // Create hospital instance
    hospital = new Hospital(mockConnection);

    // Run method
    int nextId = hospital.getNextInventoryId();

    // Assert
    assertEquals(10, nextId);
}
@Test
public void testGetNextAppointmentId() throws Exception {
    // Mocks
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.prepareStatement("SELECT MAX(appID) AS max_id FROM Appointments")).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("max_id")).thenReturn(5);  // Simulate max ID = 5
    when(mockResultSet.wasNull()).thenReturn(false);     // So we return 6

    hospital = new Hospital(mockConnection);

    int nextId = hospital.getNextAppointmentId();

    assertEquals(6, nextId);
}
@Test
public void testGetNextRecordId() throws Exception {
    // Mocking
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(recordID) + 1 AS next_id FROM MedicalRecords"))
        .thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(10); // Simulate next ID = 10

    hospital = new Hospital(mockConnection);

    int nextId = hospital.getNextRecordId();

    assertEquals(10, nextId);
}
//test the case where the result is 0
@Test
public void testGetNextRecordId_EmptyTable() throws Exception {
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(recordID) + 1 AS next_id FROM MedicalRecords"))
        .thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(0); // Simulate null/empty table

    hospital = new Hospital(mockConnection);

    int nextId = hospital.getNextRecordId();

    assertEquals(1, nextId); // Should default to

}
@Test
public void testGetNextBillId() throws Exception {
    // Mocks
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(billId) + 1 AS next_id FROM Bills"))
        .thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(15); // Simulate next ID = 15

    hospital = new Hospital(mockConnection);

    int nextId = hospital.getNextBillId();

    assertEquals(15, nextId);
}
@Test
public void testGetNextBillId_EmptyTable() throws Exception {
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    when(mockConnection.createStatement()).thenReturn(mockStatement);
    when(mockStatement.executeQuery("SELECT MAX(billId) + 1 AS next_id FROM Bills"))
        .thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true);
    when(mockResultSet.getInt("next_id")).thenReturn(0); // Simulate empty table

    hospital = new Hospital(mockConnection);

    int nextId = hospital.getNextBillId();

    assertEquals(1, nextId); // Should default to 1
}

@Test
public void testGetPatients() throws Exception {
    // Create mock Patient objects
    Patient patient1 = new Patient(1, "John Doe", 30, "Male", "123 Street", "555-1234");
    Patient patient2 = new Patient(2, "Jane Doe", 25, "Female", "456 Street", "555-5678");

    // Mock the patients list
    List<Patient> mockPatients = new ArrayList<>();
    mockPatients.add(patient1);
    mockPatients.add(patient2);

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getPatients()).thenReturn(mockPatients);

    // Call the method
    List<Patient> patients = hospital.getPatients();

    // Verify the results
    assertEquals(2, patients.size());
    assertEquals("John Doe", patients.get(0).getName());
    assertEquals("Jane Doe", patients.get(1).getName());
}
@Test
public void testGetAppointments() throws Exception {
    Appointment appointment1 = new Appointment(1, 1, "Checkup", Date.valueOf("2025-04-14"), Time.valueOf("10:00:00"), 101);
    Appointment appointment2 = new Appointment(2, 2, "Follow-up", Date.valueOf("2025-04-15"), Time.valueOf("11:00:00"), 102);

    List<Appointment> mockAppointments = new ArrayList<>();
    mockAppointments.add(appointment1);
    mockAppointments.add(appointment2);

    hospital = mock(Hospital.class);
    when(hospital.getAppointments()).thenReturn(mockAppointments);

    List<Appointment> appointments = hospital.getAppointments();

    assertEquals(2, appointments.size());
    assertEquals("Checkup", appointments.get(0).getType());
    assertEquals("Follow-up", appointments.get(1).getType());
}
@Test
public void testGetRecords() throws Exception {
    // Create mock MedicalRecord objects
    MedicalRecord record1 = new MedicalRecord(1, 1, "Flu", "Rest and Hydration", Date.valueOf("2025-04-14"));
    MedicalRecord record2 = new MedicalRecord(2, 2, "Cold", "Antihistamines", Date.valueOf("2025-04-15"));

    // Mock the records list
    List<MedicalRecord> mockRecords = new ArrayList<>();
    mockRecords.add(record1);
    mockRecords.add(record2);

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getRecords()).thenReturn(mockRecords);

    // Call the method
    List<MedicalRecord> records = hospital.getRecords();

    // Verify the results
    assertEquals(2, records.size());
    assertEquals("Flu", records.get(0).getDiagnosis());
    assertEquals("Cold", records.get(1).getDiagnosis());
}
@Test
public void testGetInventory() throws Exception {
    // Create mock InventoryItem objects
    InventoryItem item1 = new InventoryItem(1, "Bandages", 50);
    InventoryItem item2 = new InventoryItem(2, "Syringes", 100);

    // Mock the inventory list
    List<InventoryItem> mockInventory = new ArrayList<>();
    mockInventory.add(item1);
    mockInventory.add(item2);

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getInventory()).thenReturn(mockInventory);

    // Call the method
    List<InventoryItem> inventory = hospital.getInventory();

    // Verify the results
    assertEquals(2, inventory.size());
    assertEquals("Bandages", inventory.get(0).getName());
    assertEquals("Syringes", inventory.get(1).getName());
}
@Test
public void testGetDoctors() throws Exception {
    // Create mock Doctor objects
    Doctor doctor1 = new Doctor(1, "Dr. Smith", 45, "Male", "Cardiology");
    Doctor doctor2 = new Doctor(2, "Dr. Brown", 40, "Female", "Neurology");
    

    // Mock the doctors list
    List<Doctor> mockDoctors = new ArrayList<>();
    mockDoctors.add(doctor1);
    mockDoctors.add(doctor2);

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getDoctors()).thenReturn(mockDoctors);

    // Call the method
    List<Doctor> doctors = hospital.getDoctors();

    // Verify the results
    assertEquals(2, doctors.size());
    assertEquals("Dr. Smith", doctors.get(0).getName());
    assertEquals("Dr. Brown", doctors.get(1).getName());
}
@Test
public void testGetDepartments() throws Exception {
    // Create mock Department list
    List<String> mockDepartments = Arrays.asList("Cardiology", "Neurology");

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getDepartments()).thenReturn(mockDepartments);

    // Call the method
    List<String> departments = hospital.getDepartments();

    // Verify the results
    assertEquals(2, departments.size());
    assertEquals("Cardiology", departments.get(0));
    assertEquals("Neurology", departments.get(1));
}
@Test
public void testGetBills() throws Exception {
    // Create mock Bill objects
    Bill bill1 = new Bill(1, 1, 150.0, Date.valueOf("2025-04-14"));
    Bill bill2 = new Bill(2, 2, 200.0, Date.valueOf("2025-04-15"));

    // Mock the bills list
    List<Bill> mockBills = new ArrayList<>();
    mockBills.add(bill1);
    mockBills.add(bill2);

    // Create a mock Hospital object
    hospital = mock(Hospital.class);
    when(hospital.getBills()).thenReturn(mockBills);

    // Call the method
    List<Bill> bills = hospital.getBills();

    // Verify the results
    assertEquals(2, bills.size());
    assertEquals(150.0, bills.get(0).getAmount(), 0.01);
    assertEquals(200.0, bills.get(1).getAmount(), 0.01);
}

}
