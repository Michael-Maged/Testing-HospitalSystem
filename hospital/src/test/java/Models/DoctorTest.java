package Models;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.hospital.Doctor;

class DoctorTest {

    @Test
    void testConstructorAndGetters() {
        Doctor doctor = new Doctor(101, "Dr. Smith", 45, "Male", "Cardiology");

        assertEquals(101, doctor.getDoctorID());
        assertEquals("Dr. Smith", doctor.getName());
        assertEquals(45, doctor.getAge());
        assertEquals("Male", doctor.getGender());
        assertEquals("Cardiology", doctor.getSpecialty());
    }

    @Test
    void testSetters() {
        Doctor doctor = new Doctor(0, "", 0, "", "");

        doctor.setDoctorID(202);
        doctor.setName("Dr. Alice");
        doctor.setAge(38);
        doctor.setGender("Female");
        doctor.setSpecialty("Neurology");

        assertEquals(202, doctor.getDoctorID());
        assertEquals("Dr. Alice", doctor.getName());
        assertEquals(38, doctor.getAge());
        assertEquals("Female", doctor.getGender());
        assertEquals("Neurology", doctor.getSpecialty());
    }

    @Test
    void testToString() {
        Doctor doctor = new Doctor(303, "Dr. John", 50, "Male", "Orthopedics");
        String expected = "Doctor{doctorID=303, name='Dr. John', specialty='Orthopedics'}";
        assertEquals(expected, doctor.toString());
    }
}
