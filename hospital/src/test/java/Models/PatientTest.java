package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hospital.Appointment;
import com.hospital.Bill;
import com.hospital.MedicalRecord;
import com.hospital.Patient;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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

        Bill bill = new Bill(101, 250.0, new Date(System.currentTimeMillis()));

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

        Bill bill = new Bill(101, 250.0, new Date(System.currentTimeMillis()));
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
