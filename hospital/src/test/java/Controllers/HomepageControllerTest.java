package Controllers;

import com.hospital.controllers.HomepageController;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class HomepageControllerTest {

    @InjectMocks
    private HomepageController homepageController;  // Use HomepageController, not HomepageControllerTest

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
    void testShowHomepage_ReturnsHomepageView() {
        // Call the method
        String viewName = homepageController.showHomepage();

        // Assert that the view name is correct
        assertEquals("homepage", viewName);
    }
}
