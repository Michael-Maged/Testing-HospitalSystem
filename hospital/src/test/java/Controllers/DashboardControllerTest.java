package Controllers;

import com.hospital.*;
import com.hospital.controllers.DashboardController;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.Model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    @InjectMocks
    private DashboardController controller;

    @Mock
    private Hospital hospital;

    @Mock
    private Patient patient;

    @Mock
    private Model model;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Session testSession = Session.getInstance();
        testSession.setCurrentPatient(patient); // Assuming you modified the Session class to allow setting current patient for testing
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testShowDashboard_WhenPatientIsLoggedIn_ReturnsDashboard() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        ArrayList<MedicalRecord> records = new ArrayList<>();
        ArrayList<Doctor> doctors = new ArrayList<>();

        when(patient.FetchUserAppointments()).thenReturn(appointments);
        when(patient.FetchUserRecords()).thenReturn(records);
        when(hospital.getDoctors()).thenReturn(doctors);

        String viewName = controller.showDashboard(model);

        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute("appointments", appointments);
        verify(model).addAttribute("records", records);
        verify(model).addAttribute("hospital", hospital);
        verify(model).addAttribute("doctors", doctors);
        assertEquals("dashboard", viewName);
    }

    @Test
    void testShowDashboard_WhenNoPatientLoggedIn_ReturnsLogin() {
        Session.getInstance().setCurrentPatient(null);
        String viewName = controller.showDashboard(model);

        verify(model).addAttribute(eq("error"), anyString());
        assertEquals("patient-login", viewName);
    }

    @Test
    void testAddAppointment_ValidInput_AddsAppointment() {
        Date date = Date.valueOf("2024-05-01");
        String time = "10:30";
        String doctorName = "Dr. Smith";

        Doctor doctor = new Doctor(1, doctorName, 40, "Male", "Cardiology");

        when(hospital.getNextAppointmentId()).thenReturn(1);
        when(hospital.findDoctorByName(doctorName)).thenReturn(doctor);

        String viewName = controller.addAppointment(date, time, doctorName, model);

        verify(patient).addAppointment(any(Appointment.class));
        assertEquals("redirect:/dashboard", viewName);
    }

    @Test
    void testAddAppointment_WhenNoPatientLoggedIn_ReturnsLogin() {
        Session.getInstance().setCurrentPatient(null);
        Date date = Date.valueOf("2024-05-01");
        String time = "10:30";
        String doctorName = "Dr. Smith";

        String viewName = controller.addAppointment(date, time, doctorName, model);

        verify(model).addAttribute(eq("error"), anyString());
        assertEquals("patient-login", viewName);
    }

    @Test
    void testDeleteAppointment_RemovesAppointment() {
Appointment appointment = new Appointment(5, 1, "Cardiology", Date.valueOf("2024-05-01"), Time.valueOf("10:30:00"), 1);
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(hospital.getAppointments()).thenReturn(appointments);
        when(hospital.findById(appointments, 5)).thenReturn(appointment);

        String viewName = controller.deleteAppointment(5);

        verify(hospital).cancelAppointment(5);
        assertEquals("redirect:/dashboard", viewName);
    }
}
