package Controllers;

import com.hospital.Patient;
import com.hospital.Session;
import com.hospital.Hospital;
import com.hospital.controllers.PatientLoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatientLoginControllerTest {

    private Hospital mockHospital;
    private Model mockModel;
    private PatientLoginController controller;

    @BeforeEach
    public void setUp() {
        mockHospital = Mockito.mock(Hospital.class);
        mockModel = Mockito.mock(Model.class);
        controller = new PatientLoginController() {
            {
                // Override the hospital field with a mock
                try {
                    java.lang.reflect.Field hospitalField = PatientLoginController.class.getDeclaredField("hospital");
                    hospitalField.setAccessible(true);
                    hospitalField.set(this, mockHospital);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testShowLoginForm_ReturnsLoginPage() {
        String view = controller.showLoginForm(mockModel);
        assertEquals("patient-login", view);
    }

    @Test
    public void testHandleLogin_ValidCredentials_RedirectsToDashboard() {
        Patient mockPatient = new Patient(1, "Test User", 30, "Male", "street", "01279983");
        Session.getInstance().setCurrentPatient(mockPatient);

        String name = "Test User";
        String phone = "1234567890";

        doAnswer(ignored -> {
            Session.getInstance().setCurrentPatient(mockPatient);
            return null;
        }).when(mockHospital).loginPatient(phone, name);

        String view = controller.handleLogin(name, phone, mockModel);
        assertEquals("redirect:/dashboard", view);
    }

    @Test
    public void testHandleLogin_InvalidCredentials_ReturnsLoginPageWithError() {
        Session.getInstance().setCurrentPatient(null);

        String name = "Wrong";
        String phone = "000";

        doAnswer(ignored -> {
            Session.getInstance().setCurrentPatient(null);
            return null;
        }).when(mockHospital).loginPatient(phone, name);

        String view = controller.handleLogin(name, phone, mockModel);

        assertEquals("patient-login", view);
        verify(mockModel).addAttribute("error", "Invalid name or phone");
    }

    @Test
    public void testHandleLogin_Exception_ReturnsLoginPageWithError() {
        String name = "Exception";
        String phone = "000";

        doThrow(new RuntimeException("Simulated error")).when(mockHospital).loginPatient(phone, name);

        String view = controller.handleLogin(name, phone, mockModel);

        assertEquals("patient-login", view);
        verify(mockModel).addAttribute("error", "Something went wrong");
    }
}
