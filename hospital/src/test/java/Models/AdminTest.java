package Models;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.hospital.Admin;

public class AdminTest {

    @Test
    public void testAddDoctor() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).addDoctor(anyInt(), anyString(), anyInt(), anyString(), anyString());

        adminMock.addDoctor(1, "Dr. Smith", 45, "Male", "Cardiology");
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addDoctor(1, "Dr. Smith", 45, "Male", "Cardiology");
    }

    @Test
    public void testDeleteDoctor() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).deleteDoctor(anyInt());
        
        adminMock.deleteDoctor(1);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteDoctor(1);
    }

    @Test
    public void testAddInventoryItem() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).addInventoryItem(anyInt(), anyString(), anyInt());
        
        adminMock.addInventoryItem(1, "Item A", 100);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addInventoryItem(1, "Item A", 100);
    }

    @Test
    public void testDeleteInventoryItem() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).deleteInventoryItem(anyInt());
        
        adminMock.deleteInventoryItem(1);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteInventoryItem(1);
    }

    @Test
    public void testAddMedicalRecord() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).addMedicalRecord(anyInt(), anyInt(), anyString(), anyString(), any());
        
        adminMock.addMedicalRecord(1, 123, "Diagnosis", "Treatment", null);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).addMedicalRecord(1, 123, "Diagnosis", "Treatment", null);
    }

    @Test
    public void testDeleteMedicalRecord() {
        Admin adminMock = mock(Admin.class);
        doNothing().when(adminMock).deleteMedicalRecord(anyLong());
        
        adminMock.deleteMedicalRecord(1L);
        
        // Verify interaction with the mock
        verify(adminMock, times(1)).deleteMedicalRecord(1L);
    }
}
