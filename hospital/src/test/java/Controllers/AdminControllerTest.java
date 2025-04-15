package Controllers;

import com.hospital.*;
import com.hospital.controllers.AdminController;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController controller;

    @Mock
    private Hospital hospital;

    @Mock
    private Admin admin;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetAdminDashboard_ReturnsAdminPage() {
        when(hospital.getDoctors()).thenReturn(new ArrayList<>());
        when(hospital.getInventory()).thenReturn(new ArrayList<>());
        when(hospital.getRecords()).thenReturn(new ArrayList<>());
        when(hospital.getDepartments()).thenReturn(new ArrayList<>());
        when(hospital.getPatients()).thenReturn(new ArrayList<>());
        when(hospital.getBills()).thenReturn(new ArrayList<>());

        String viewName = controller.getAdminDashboard(model);

        assertEquals("adminPage", viewName);
    }

    @Test
    void testAddDoctor_InvalidName_RedirectsWithError() {
        String invalidName = "1234";
        String result = controller.addDoctor(30, invalidName, "Male", "Cardiology", model);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddDoctor_InvalidAge_RedirectsWithError() {
        String name = "John";
        String result = controller.addDoctor(22, name, "Male", "Cardiology", model);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddDoctor_ValidInput_AddsDoctor() {
        when(hospital.getNextDoctorId()).thenReturn(1);
        String result = controller.addDoctor(40, "Alice", "Female", "Pediatrics", model);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testDeleteDoctor_RemovesDoctor() {
        Doctor doctor = new Doctor(1, "Ali", 30, "Male", "Surgery");
        List<Doctor> doctors = new ArrayList<>(Collections.singletonList(doctor));
        when(hospital.getDoctors()).thenReturn(doctors);
        when(hospital.findById(doctors, 1)).thenReturn(doctor);

        String result = controller.deleteDoctor(1);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddInventory_InvalidName() {
        String result = controller.addInventory("123", 10, model);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddInventory_NegativeQuantity() {
        String result = controller.addInventory("Bandage", -5, model);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddInventory_ValidInput() {
when(hospital.getNextInventoryId());
    }
}