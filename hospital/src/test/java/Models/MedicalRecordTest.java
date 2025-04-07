package Models;

import org.junit.jupiter.api.Test;

import com.hospital.MedicalRecord;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordTest {
    
@Test
    public void testConstructorAndGetters() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(1L, 1001L, "Flu", "Rest and hydration");

        // Verify the values are correctly set
        assertEquals(1L, record.getRecordID());
        assertEquals(1001L, record.getPatientID());
        assertEquals("Flu", record.getDiagnosis());
        assertEquals("Rest and hydration", record.getTreatment());
    }

    @Test
    public void testSetters() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(2L, 1002L, "Cold", "Rest and fluids");

        // Use setters to update values
        record.setRecordID(3L);
        record.setPatientID(1003L);
        record.setDiagnosis("Pneumonia");
        record.setTreatment("Antibiotics");

        // Verify that setters correctly update the values
        assertEquals(3L, record.getRecordID());
        assertEquals(1003L, record.getPatientID());
        assertEquals("Pneumonia", record.getDiagnosis());
        assertEquals("Antibiotics", record.getTreatment());
    }

    @Test
    public void testToString() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(1L, 1001L, "Flu", "Rest and hydration");

        // Expected string format
        String expectedOutput = "Patient 1001: Diagnosis: Flu, Treatment: Rest and hydration";

        // Verify the toString() method works as expected
        assertEquals(expectedOutput, record.toString());
    }    
}
