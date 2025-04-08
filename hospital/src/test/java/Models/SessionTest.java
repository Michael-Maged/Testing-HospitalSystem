package Models;

import com.hospital.Patient;
import com.hospital.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        // This will initialize a new session before each test
        session = Session.getInstance();
    }

    @Test
    public void testSingleton() {
        // Ensure that calling getInstance() always returns the same instance
        Session anotherSession = Session.getInstance();
        assertSame(session, anotherSession, "Session should be a singleton.");
    }

    @Test
    public void testSetAndGetCurrentPatient() {
        // Set a patient
        Patient patient = new Patient(1, "John Doe", 30, "Male", "123 Street", "555-1234");
        session.setCurrentPatient(patient);

        // Assert that the patient set is the same as the current patient
        assertEquals(patient, session.getCurrentPatient(), "The current patient should be the one that was set.");
    }

    @Test
    public void testClearSession() {
        // Set a patient
        Patient patient = new Patient(1, "John Doe", 30, "Male", "123 Street", "555-1234");
        session.setCurrentPatient(patient);

        // Now, clear the session
        session.clearSession();

        // Assert that the session is cleared and the current patient is null
        assertNull(session.getCurrentPatient(), "After clearing the session, the current patient should be null.");
    }
}
