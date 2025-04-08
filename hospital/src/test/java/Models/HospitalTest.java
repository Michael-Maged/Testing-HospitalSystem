package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.hospital.Appointment;
import com.hospital.Bill;
import com.hospital.MedicalRecord;
import com.hospital.Patient;
import com.hospital.Hospital;
import com.hospital.Session;

import java.sql.*;
import java.util.*;



import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {

    private Hospital hospital;

    @BeforeEach
    public void setUp() throws Exception {
        hospital = new Hospital();
        clearDatabase();
    }

    private void clearDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false", "testing", "mypass");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM MedicalRecords");
            stmt.executeUpdate("DELETE FROM Appointments");
            stmt.executeUpdate("DELETE FROM Patients");
            stmt.executeUpdate("DBCC CHECKIDENT ('Patients', RESEED, 0)");
            stmt.executeUpdate("DBCC CHECKIDENT ('Appointments', RESEED, 0)");
            stmt.executeUpdate("DBCC CHECKIDENT ('MedicalRecords', RESEED, 0)");
        }
    }

    @Test
    public void testRegisterAndLoginPatient() {
        hospital.registerPatient("Alice", 22, "Female", "Cairo", "0111222333");
        hospital.fetchPatients();
        List<Patient> patients = hospital.getPatients();

        assertEquals(1, patients.size());
        assertEquals("Alice", patients.get(0).getName());

        hospital.loginPatient("0111222333", "Alice");
        assertNotNull(Session.getInstance().getCurrentPatient());
        assertEquals("Alice", Session.getInstance().getCurrentPatient().getName());
    }

    @Test
    public void testScheduleAppointment() {
        hospital.registerPatient("Bob", 30, "Male", "Giza", "0123456789");
        int patientId = hospital.getPatients().get(0).getPatientID();

        hospital.scheduleAppointment(1, patientId, "Checkup", java.sql.Date.valueOf("2025-04-09"), Time.valueOf("12:30:00"), 1);
        hospital.fetchAppointments();
        List<Appointment> appointments = hospital.getAppointments();

        assertEquals(1, appointments.size());
        assertEquals("Checkup", appointments.get(0).getType());
    }

    @Test
    public void testAddMedicalRecord() {
        hospital.registerPatient("Clara", 28, "Female", "Alex", "0100000000");
        int patientId = hospital.getPatients().get(0).getPatientID();

        hospital.addMedicalRecord(1, patientId, "Flu", "Rest & Fluids");
        hospital.fetchMedicalRecords(patientId);

        List<MedicalRecord> records = hospital.getRecords();
        assertEquals(1, records.size());
        assertEquals("Flu", records.get(0).getDiagnosis());
    }

    @Test
    public void testFetchDoctorsInventory() {
        hospital.fetchDoctors();
        hospital.fetchInventoryItems();

        assertNotNull(hospital.getDoctors());
        assertNotNull(hospital.getInventory());
    }

    @Test
    public void testFindByIdGeneric() {
        Patient p = new Patient(101, "Test", 23, "Female", "Somewhere", "010999999");
        List<Patient> testList = new ArrayList<>();
        testList.add(p);

        Patient result = hospital.findById(testList, 101);
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @AfterEach
    public void tearDown() {
        Session.getInstance().setCurrentPatient(null);
    }
}
