package Models;

import com.hospital.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient(1, "John Doe", 30, "Male", "123 Street", "01001234567");
    }

    @Test
    public void testPatientInitialization() {
        assertEquals(1L, patient.getPatientID());
        assertEquals("John Doe", patient.getName());
        assertEquals(30, patient.getAge());
        assertEquals("Male", patient.getGender());
        assertEquals("123 Street", patient.getAddress());
        assertEquals("01001234567", patient.getPhoneNumber());
    }

    @Test
    public void testSetters() {
        patient.setName("Jane Smith");
        patient.setAge(40);
        patient.setGender("Female");
        patient.setAddress("456 Avenue");
        patient.setPhoneNumber("0111222333");

        assertEquals("Jane Smith", patient.getName());
        assertEquals(40, patient.getAge());
        assertEquals("Female", patient.getGender());
        assertEquals("456 Avenue", patient.getAddress());
        assertEquals("0111222333", patient.getPhoneNumber());
    }

    @Test
    public void testAddBill() {
        Bill bill = new Bill(101, 200.5, new Date());
        patient.addBill(bill);
        assertEquals(1, patient.getBills().size());
        assertEquals(bill, patient.getBills().get(0));
    }

    @Test
    public void testAddAppointment() {
        Appointment appointment = new Appointment(201, 1, "Checkup", new Date(), new java.sql.Time(System.currentTimeMillis()), 501);
        patient.addAppointment(appointment);
        assertEquals(1, patient.getAppointments().size());
        assertEquals(appointment.getAppID(), patient.getAppointments().get(0).getAppID());
    }
}
