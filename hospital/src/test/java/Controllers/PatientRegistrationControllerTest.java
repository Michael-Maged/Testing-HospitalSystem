package Controllers;

import com.hospital.Hospital;
import com.hospital.controllers.PatientRegistrationController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PatientRegistrationControllerTest {

    @InjectMocks
    private PatientRegistrationController controller;

    @Mock
    private Hospital hospital;

    @Mock
    private Model model;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize mocks
        closeable = MockitoAnnotations.openMocks(this);
        // Override the hospital field in the controller with our mock using reflection
        Field hospitalField = PatientRegistrationController.class.getDeclaredField("hospital");
        hospitalField.setAccessible(true);
        hospitalField.set(controller, hospital);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testShowRegistrationForm_ReturnsPatientRegistration() {
        // When GET /register is called, hospital.fetchPatients() is invoked and "patient-registration" is returned.
        String viewName = controller.showRegistrationForm();
        verify(hospital).fetchPatients();
        assertEquals("patient-registration", viewName);
    }

    @Test
    void testRegisterPatient_InvalidName() {
        // Use an invalid name with non-letter characters.
        String view = controller.registerPatient("1234", 25, "Male", "some address", "01234567890", model);
        verify(model).addAttribute(eq("error"), contains("Invalid name"));
        assertEquals("patient-registration", view);
    }

    @Test
    void testRegisterPatient_InvalidAge() {
        // Age must be positive; using 0 should trigger the validation error.
        String view = controller.registerPatient("John Doe", 0, "Male", "some address", "01234567890", model);
        verify(model).addAttribute(eq("error"), contains("Age must be a positive number"));
        assertEquals("patient-registration", view);
    }

    @Test
    void testRegisterPatient_InvalidPhone() {
        // Phone number doesn't match the regex pattern (must be 11 digits starting with 012 or 010).
        String view = controller.registerPatient("John Doe", 30, "Male", "some address", "123456", model);
        verify(model).addAttribute(eq("error"), contains("Phone number must be 11 digits"));
        assertEquals("patient-registration", view);
    }

    @Test
    void testRegisterPatient_DuplicatePhone() {
        // When the given phone number already exists, the method should set an error.
        when(hospital.phoneExists("01234567890")).thenReturn(true);
        String view = controller.registerPatient("John Doe", 30, "Male", "some address", "01234567890", model);
        verify(model).addAttribute(eq("error"), contains("Phone number is already registered"));
        assertEquals("patient-registration", view);
    }

    @Test
    void testRegisterPatient_ValidInput() {
        // When all inputs are valid and the phone number does not exist.
        when(hospital.phoneExists("01234567890")).thenReturn(false);
        String view = controller.registerPatient("John Doe", 30, "Male", "some address", "01234567890", model);
        verify(hospital).registerPatient("John Doe", 30, "Male", "some address", "01234567890");
        assertEquals("patient-login", view);
    }
}
