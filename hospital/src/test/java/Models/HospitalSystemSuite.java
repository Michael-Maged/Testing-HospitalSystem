package Models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    HospitalTest.class,
    PatientTest.class,
    DoctorTest.class,
    AppointmentTest.class,
    AdminTest.class,
    BillTest.class,
    InventoryItemTest.class,
    MedicalRecordTest.class,
    SessionTest.class
})
public class HospitalSystemSuite {
    @Test
    void suiteRuns() {
        assertTrue(true, "Dummy test to ensure suite runs");
    }
}
