package Models;

import org.junit.jupiter.api.Test;

import com.hospital.MedicalRecord;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

public class MedicalRecordTest {
    
@Test
    public void testConstructorAndGetters() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(1, 1001, "Flu", "Rest and hydration", Date.valueOf("2025-04-07"));

        // Verify the values are correctly set
        assertEquals(1, record.getRecordID());
        assertEquals(1001, record.getPatientID());
        assertEquals("Flu", record.getDiagnosis());
        assertEquals("Rest and hydration", record.getTreatment());
    }

    @Test
    public void testSetters() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(2, 1002, "Cold", "Rest and fluids", Date.valueOf("2025-04-07"));

        // Use setters to update values
        record.setRecordID(3);
        record.setPatientID(1003);
        record.setDiagnosis("Pneumonia");
        record.setTreatment("Antibiotics");

        // Verify that setters correctly update the values
        assertEquals(3, record.getRecordID());
        assertEquals(1003, record.getPatientID());
        assertEquals("Pneumonia", record.getDiagnosis());
        assertEquals("Antibiotics", record.getTreatment());
    }

    @Test
    public void testToString() {
        // Create a MedicalRecord instance
        MedicalRecord record = new MedicalRecord(1, 1001, "Flu", "Rest and hydration", Date.valueOf("2025-04-07"));

        // Expected string format
        String expectedOutput = "Patient 1001: Diagnosis: Flu, Treatment: Rest and hydration";

        // Verify the toString() method works as expected
        assertEquals(expectedOutput, record.toString());
    }    
}
