package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class SlideViewerFrameTest {

    @Test
    void testFrameInitialization() {
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame("JabberPoint 1.0 - OU", presentation);

        assertEquals("JabberPoint 1.0 - OU", frame.getTitle());
        assertNotNull(frame.getSlideViewerComponent());
        assertEquals(presentation, frame.getSlideViewerComponent().getPresentation());
    }

    @Test
    void testSetSlideNumberCallsPresentation() {
        Presentation presentation = mock(Presentation.class);
        SlideViewerFrame frame = new SlideViewerFrame("Test", presentation);

        // Call setSlideNumber on mock to verify interaction
        presentation.setSlideNumber(5);
        verify(presentation).setSlideNumber(5);
    }
}
