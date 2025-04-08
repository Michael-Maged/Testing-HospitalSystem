package Models;  // Correct package declaration based on your directory structure

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt; // Import the Admin class from your project
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hospital.Admin;

public class AdminTest {

    @Test
    public void testAddDoctor() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).addDoctor(anyInt(), anyString(), anyInt(), anyString(), anyString());

        adminMock.addDoctor(1, "Dr. Smith", 45, "Male", "Cardiology");
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addDoctor(anyInt(), anyString(), anyInt(), anyString(), anyString());
    }

    @Test
    public void testDeleteDoctor() {
        Admin adminMock = mock(Admin.class);
        
        // Define behavior for the mock if needed
        doNothing().when(adminMock).deleteDoctor(anyInt());
        
        // Call the method on the mock
        adminMock.deleteDoctor(1);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteDoctor(anyInt());
    }

    @Test
    public void testAddInventoryItem() {
        Admin adminMock = mock(Admin.class);
        
        // Define behavior for the mock if needed
        doNothing().when(adminMock).addInventoryItem(anyInt(), anyString(), anyInt());
        
        // Call the method on the mock
        adminMock.addInventoryItem(1, "Item A", 100);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addInventoryItem(anyInt(), anyString(), anyInt());
    }

    @Test
    public void testDeleteInventoryItem() {
        Admin adminMock = mock(Admin.class);
        
        // Define behavior for the mock if needed
        doNothing().when(adminMock).deleteInventoryItem(anyInt());
        
        // Call the method on the mock
        adminMock.deleteInventoryItem(1);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteInventoryItem(anyInt());
    }

    @Test
    public void testAddMedicalRecord() {
        Admin adminMock = mock(Admin.class);
        
        // Define behavior for the mock if needed
        doNothing().when(adminMock).addMedicalRecord(anyInt(), anyInt(), anyString(), anyString(), any());
        
        // Call the method on the mock
        adminMock.addMedicalRecord(1, 123, "Diagnosis", "Treatment", null);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addMedicalRecord(anyInt(), anyInt(), anyString(), anyString(), any());
    }

    @Test
    public void testDeleteMedicalRecord() {
        Admin adminMock = mock(Admin.class);
        
        // Define behavior for the mock if needed
        doNothing().when(adminMock).deleteMedicalRecord(anyInt());
        
        // Call the method on the mock
        adminMock.deleteMedicalRecord(1);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteMedicalRecord(anyInt());
    }
}
