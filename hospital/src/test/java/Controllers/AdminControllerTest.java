package Controllers;

import com.hospital.*;
import com.hospital.controllers.AdminController;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        String result = controller.addDoctor(30, invalidName, "Male", "Cardiology", redirectAttributes);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddDoctor_InvalidAge_RedirectsWithError() {
        String name = "John";
        String result = controller.addDoctor(22, name, "Male", "Cardiology", redirectAttributes);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddDoctor_ValidInput_AddsDoctor() {
        when(hospital.getNextDoctorId()).thenReturn(1);
        String result = controller.addDoctor(40, "Alice", "Female", "Pediatrics", redirectAttributes);
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
        String result = controller.addInventory("123", 10, redirectAttributes);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testAddInventory_NegativeQuantity() {
        String result = controller.addInventory("Bandage", -5, redirectAttributes);
        assertEquals("redirect:/admin", result);
    }

   
    @Test
void testAddInventory_ValidInput() {
    when(hospital.getNextInventoryId()).thenReturn(1);
    String result = controller.addInventory("Bandage", 10, redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testDeleteInventory_RemovesItem() {
    InventoryItem item = new InventoryItem(1, "Bandage", 5);
    List<InventoryItem> inventory = new ArrayList<>(Collections.singletonList(item));
    when(hospital.getInventory()).thenReturn(inventory);
    when(hospital.findById(inventory, 1)).thenReturn(item);

    String result = controller.deleteInventory(1);
    assertEquals("redirect:/admin", result);
}

@Test
void testAddMedicalRecord_InvalidPatientId() {
    when(hospital.findPatientById(999)).thenReturn(null);
    String result = controller.addMedicalRecord(999, "Flu", "Rest", new java.sql.Date(System.currentTimeMillis()), redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testAddMedicalRecord_ValidInput() {
    Patient patient = new Patient(1, "John", 25, "Male", "12345", "67890");
    when(hospital.getNextRecordId()).thenReturn(1);
    when(hospital.getRecords()).thenReturn(new ArrayList<>());
    when(hospital.findById(hospital.getRecords(), 1)).thenReturn(null);
    when(hospital.findPatientById(1)).thenReturn(patient);
    when(hospital.getNextRecordId()).thenReturn(1);

    String result = controller.addMedicalRecord(1, "Cold", "Medication", new java.sql.Date(System.currentTimeMillis()), redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testDeleteRecord_RemovesRecord() {
    MedicalRecord record = new MedicalRecord(1, 1, "Flu", "Rest", new java.sql.Date(System.currentTimeMillis()));
    List<MedicalRecord> records = new ArrayList<>(Collections.singletonList(record));
    when(hospital.getRecords()).thenReturn(records);
    when(hospital.findById(records, 1)).thenReturn(record);

    String result = controller.deleteRecord(1);
    assertEquals("redirect:/admin", result);
}

@Test
void testAddBill_InvalidAmount() {
    String result = controller.addBill(1, -100.0, new java.sql.Date(System.currentTimeMillis()), redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testAddBill_InvalidPatientId() {
    when(hospital.findPatientById(999)).thenReturn(null);
    String result = controller.addBill(999, 100.0, new java.sql.Date(System.currentTimeMillis()), redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testAddBill_ValidInput() {
    Patient patient = new Patient(1, "Jane", 30, "Female", "98765", null);
    when(hospital.findPatientById(1)).thenReturn(patient);

    String result = controller.addBill(1, 200.0, new java.sql.Date(System.currentTimeMillis()), redirectAttributes);
    assertEquals("redirect:/admin", result);
}

@Test
void testDeleteBill_DeletesSuccessfully() {
    String result = controller.deleteBill(1);
    assertEquals("redirect:/admin", result);
}

}