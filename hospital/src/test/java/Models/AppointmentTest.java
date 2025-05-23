package Models;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.Time;    
import com.hospital.Appointment;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    
     @Test

    public void testConstructorAndGetters() {
        // Create an Appointment instance
        Appointment appointment = new Appointment(1, 101, "Consultation", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 201);

        // Verify the values are correctly set
        assertEquals(1, appointment.getAppID());
        assertEquals(101, appointment.getPatientID());
        assertEquals(201, appointment.getDocID());
        assertEquals("Consultation", appointment.getType());
        assertNotNull(appointment.getDate());
        assertNotNull(appointment.getTime());
    }

    @Test
    public void testSetters() {
        // Create an Appointment instance
        Appointment appointment = new Appointment(2, 102, "Checkup", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 202);

        // Use setters to update values
        appointment.setAppID(3);
        appointment.setPatientID(103);
        appointment.setDocID(203);
        appointment.setType("Surgery");
        appointment.setDate(new Date(System.currentTimeMillis() + 100000));
        appointment.setTime(new Time(System.currentTimeMillis() + 100000));

        // Verify that setters correctly update the values
        assertEquals(3, appointment.getAppID());
        assertEquals(103, appointment.getPatientID());
        assertEquals(203, appointment.getDocID());
        assertEquals("Surgery", appointment.getType());
        assertNotNull(appointment.getDate());
        assertNotNull(appointment.getTime());
    }

    @Test
    public void testToString() {
        // Create an Appointment instance
        Appointment appointment = new Appointment(4, 104, "Emergency", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 204);

        // Expected string representation
        String expectedString = "Appointment for patient 104 with Dr. 204 on " + appointment.getDate();

        // Verify the string representation is as expected
        assertEquals(expectedString, appointment.toString());
    }  
}
