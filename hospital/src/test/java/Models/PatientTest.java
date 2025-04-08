package Models;

import org.junit.jupiter.api.Test;

import com.hospital.Appointment;
import com.hospital.Bill;
import com.hospital.Patient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void testConstructorAndGetters() {
        Patient patient = new Patient(1L, "Alice", 25, "Female", "123 Main St", "555-1234");

        assertEquals(1L, patient.getPatientID());
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
        Patient patient = new Patient(2L, "Bob", 30, "Male", "456 Elm St", "555-5678");

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
    }

    @Test
    public void testAddAppointment() {
        Patient patient = new Patient(3L, "Charlie", 40, "Male", "999 Maple Ave", "555-7890");

        Appointment app = new Appointment(10L, 3L, "Checkup",
                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 200L);

        patient.addAppointment(app);
        ArrayList<Appointment> appointments = patient.getAppointments();

        assertEquals(1, appointments.size());
        assertEquals(app, appointments.get(0));
    }

    @Test
    public void testAddBill() {
        Patient patient = new Patient(4L, "Dana", 35, "Female", "234 Cedar Rd", "555-4444");

        Bill bill = new Bill(101L, 250.0, new Date(System.currentTimeMillis()));

        patient.addBill(bill);
        ArrayList<Bill> bills = patient.getBills();

        assertEquals(1, bills.size());
        assertEquals(bill, bills.get(0));
    }

    @Test
    public void testToString() {
        Patient patient = new Patient(5L, "Eva", 28, "Female", "12 River St", "555-6789");

        Appointment app = new Appointment(20L, 5L, "Consultation",
                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 101L);
        patient.addAppointment(app);

        Bill bill = new Bill(101L, 250.0, new Date(System.currentTimeMillis()));
        patient.addBill(bill);

        String result = patient.toString();

        assertTrue(result.contains("Patient ID: 5"));
        assertTrue(result.contains("Name: Eva"));
        assertTrue(result.contains("Appointments:"));
        assertTrue(result.contains("Bills:"));
    }

    // Note: The methods getAppointments() and getRecords() rely on a database.
    // You would typically test these using a mocking framework like Mockito or H2 in-memory DB.
}
