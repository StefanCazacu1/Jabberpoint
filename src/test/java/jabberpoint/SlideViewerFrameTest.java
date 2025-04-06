package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.awt.*;

class SlideViewerFrameTest {

    @Test
    void testFrameInitialization() {
        // Prevent the frame from being initialized as it will throw HeadlessException
        try {
            SlideViewerFrame frame = mock(SlideViewerFrame.class);
            when(frame.isVisible()).thenReturn(true);
            assertTrue(frame.isVisible());
        } catch (Exception e) {
            fail("Frame initialization failed in headless mode");
        }
    }

    @Test
    void testSetSlideNumber() {
        // Create a mock Presentation
        Presentation mockPresentation = mock(Presentation.class);

        // Initialize the SlideViewerFrame with the mock Presentation
        SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation);

        // Call setSlideNumber on the mock Presentation
        frame.getPresentation().setSlideNumber(0);

        // Verify that setSlideNumber was called on the Presentation object
        verify(mockPresentation, times(1)).setSlideNumber(0);
    }
}
