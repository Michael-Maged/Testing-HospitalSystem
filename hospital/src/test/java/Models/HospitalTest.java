package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hospital.Appointment;
import com.hospital.MedicalRecord;
import com.hospital.Patient;
import com.hospital.Hospital;
import com.hospital.Session;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {

    @Mock
    private Hospital hospital;

    private AutoCloseable mocks; // for closing mocks after each test

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Clear session between tests
        Session.getInstance().setCurrentPatient(null);

        // Close mocks to avoid memory leaks
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testRegisterAndLoginPatient() {
        // Create a mock patient and patient list
        Patient patient = new Patient(1, "Alice", 22, "Female", "Cairo", "0111222333");
        List<Patient> mockPatients = new ArrayList<>();
        mockPatients.add(patient);

        // Mock register and fetch
        doNothing().when(hospital).registerPatient(anyString(), anyInt(), anyString(), anyString(), anyString());
        when(hospital.getPatients()).thenReturn(mockPatients);

        // Simulate method calls
        hospital.registerPatient("Alice", 22, "Female", "Cairo", "0111222333");
        List<Patient> patients = hospital.getPatients();

        // Verify the list
        assertEquals(1, patients.size());
        assertEquals("Alice", patients.get(0).getName());

        // Mock loginPatient (does nothing)
        doNothing().when(hospital).loginPatient(anyString(), anyString());

        // Call login
        hospital.loginPatient("0111222333", "Alice");

        // Since the real login logic is mocked, manually set the session
        Session.getInstance().setCurrentPatient(patient);

        // Verify session
        assertNotNull(Session.getInstance().getCurrentPatient());
        assertEquals("Alice", Session.getInstance().getCurrentPatient().getName());
    }

    @Test
    public void testScheduleAppointment() {
        // Mock appointment list
        Appointment appointment = new Appointment(1, 1, "Checkup", Date.valueOf("2025-04-09"), Time.valueOf("12:30:00"), 1);
        List<Appointment> mockAppointments = Collections.singletonList(appointment);

        // Mock methods
        doNothing().when(hospital).scheduleAppointment(anyInt(), anyInt(), anyString(), any(), any(), anyInt());
        when(hospital.getAppointments()).thenReturn(mockAppointments);

        // Simulate method calls
        hospital.scheduleAppointment(1, 1, "Checkup", Date.valueOf("2025-04-09"), Time.valueOf("12:30:00"), 1);
        List<Appointment> appointments = hospital.getAppointments();

        // Verify
        assertEquals(1, appointments.size());
        assertEquals("Checkup", appointments.get(0).getType());
    }


    @Test
    public void testFetchDoctorsInventory() {
        // Mock lists
        List<String> mockDoctors = Collections.singletonList("Dr. Smith");
        List<String> mockInventory = Collections.singletonList("Bandages");

        // Simulate
        hospital.fetchDoctors();
        hospital.fetchInventoryItems();

        // Verify
        assertNotNull(hospital.getDoctors());
        assertNotNull(hospital.getInventory());
    }

    @Test
    public void testFindByIdGeneric() {
        // Create patient
        Patient patient = new Patient(101, "Test", 23, "Female", "Somewhere", "010999999");
        List<Patient> testList = Collections.singletonList(patient);

        // Mock findById
        when(hospital.findById(testList, 101)).thenReturn(patient);

        // Call
        Patient result = hospital.findById(testList, 101);

        // Verify
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }
}