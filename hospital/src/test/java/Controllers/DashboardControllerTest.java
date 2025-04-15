package Controllers;

import com.hospital.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DashboardControllerTest {

    @InjectMocks
    private DashboardControllerTest controller;

    @Mock
    private Hospital hospital;

    @Mock
    private Session session;

    @Mock
    private Patient patient;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DashboardControllerTest();
        controller.hospital = hospital;
        Session.setInstance(session); // Assuming Session has a setInstance method for mocking
    }

    @Test
    public void testShowDashboard_WhenPatientLoggedIn() {
        when(session.getCurrentPatient()).thenReturn(patient);
        when(patient.FetchUserAppointments()).thenReturn(new ArrayList<>());
        when(patient.FetchUserRecords()).thenReturn(new ArrayList<>());
        when(hospital.getDoctors()).thenReturn(new ArrayList<>());

        String view = controller.showDashboard(model);

        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute(eq("appointments"), anyList());
        verify(model).addAttribute(eq("records"), anyList());
        verify(model).addAttribute("hospital", hospital);
        verify(model).addAttribute(eq("doctors"), anyList());

        assertEquals("dashboard", view);
    }

    @Test
    public void testShowDashboard_WhenNoPatientLoggedIn() {
        when(session.getCurrentPatient()).thenReturn(null);

        String view = controller.showDashboard(model);

        verify(model).addAttribute("error", "No patient logged in");
        assertEquals("patient-login", view);
    }

    @Test
    public void testAddAppointment_ValidData() {
        Date date = Date.valueOf("2024-04-01");
        String time = "10:30";
        String doctorName = "Dr. Smith";
        Doctor doctor = new Doctor(1, doctorName, 45, "Male", "Cardiology");

        when(session.getCurrentPatient()).thenReturn(patient);
        when(hospital.getNextAppointmentId()).thenReturn(100);
        when(patient.getPatientID()).thenReturn(50);
        when(hospital.findDoctorByName(doctorName)).thenReturn(doctor);

        String view = controller.addAppointment(date, time, doctorName, model);

        verify(hospital).scheduleAppointment(anyInt(), eq(50), eq("Cardiology"), eq(date), eq(Time.valueOf("10:30:00")), eq(1));
        verify(patient).addAppointment(any(Appointment.class));

        assertEquals("redirect:/dashboard", view);
    }

    @Test
    public void testAddAppointment_NoPatientLoggedIn() {
        when(session.getCurrentPatient()).thenReturn(null);

        String view = controller.addAppointment(Date.valueOf("2024-04-01"), "10:30", "Dr. Smith", model);

        verify(model).addAttribute("error", "No patient logged in");
        assertEquals("patient-login", view);
    }

    @Test
    public void testDeleteAppointment() {
        when(hospital.getAppointments()).thenReturn(new ArrayList<>());
        Appointment appointment = new Appointment(1, 1, "Cardiology", Date.valueOf("2024-04-01"), Time.valueOf("10:30:00"), 2);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(hospital.findById(appointments, 1)).thenReturn(appointment);
        when(hospital.getAppointments()).thenReturn(appointments);

        String view = controller.deleteAppointment(1);

        verify(hospital).cancelAppointment(1);
        assertEquals("redirect:/dashboard", view);
    }
}