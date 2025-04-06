package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SlideViewerComponentTest {

    @Test
    void testInitialization() {
        // Create a mock Presentation
        Presentation mockPresentation = mock(Presentation.class);

        // Create the SlideViewerComponent with the mock Presentation
        SlideViewerComponent component = new SlideViewerComponent(mockPresentation);

        // Verify that the component was correctly initialized
        assertNotNull(component);
    }
}
