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
        Appointment appointment = new Appointment(1L, 101L, "Consultation", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 201L);

        // Verify the values are correctly set
        assertEquals(1L, appointment.getAppID());
        assertEquals(101L, appointment.getPatientID());
        assertEquals(201L, appointment.getDocID());
        assertEquals("Consultation", appointment.getType());
        assertNotNull(appointment.getDate());
        assertNotNull(appointment.getTime());
    }

    @Test
    public void testSetters() {
        // Create an Appointment instance
        Appointment appointment = new Appointment(2L, 102L, "Checkup", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 202L);

        // Use setters to update values
        appointment.setAppID(3L);
        appointment.setPatientID(103L);
        appointment.setDocID(203L);
        appointment.setType("Surgery");
        appointment.setDate(new Date(System.currentTimeMillis() + 100000));
        appointment.setTime(new Time(System.currentTimeMillis() + 100000));

        // Verify that setters correctly update the values
        assertEquals(3L, appointment.getAppID());
        assertEquals(103L, appointment.getPatientID());
        assertEquals(203L, appointment.getDocID());
        assertEquals("Surgery", appointment.getType());
        assertNotNull(appointment.getDate());
        assertNotNull(appointment.getTime());
    }

    @Test
    public void testToString() {
        // Create an Appointment instance
        Appointment appointment = new Appointment(4L, 104L, "Emergency", 
            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 204L);

        // Expected string representation
        String expectedString = "Appointment for patient 104 with Dr. 204 on " + appointment.getDate();

        // Verify the string representation is as expected
        assertEquals(expectedString, appointment.toString());
    }  
}
