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
    void testUpdateTitleWithSlideNumber() {
        // Mock Presentation and stub slide number and size
        Presentation presentation = mock(Presentation.class);
        when(presentation.getSlideNumber()).thenReturn(1);
        when(presentation.getSize()).thenReturn(5);

        SlideViewerFrame frame = new SlideViewerFrame("Test", presentation);

        // Call update which uses those methods to set the title
        frame.update();

        // The title should reflect slide number (1 + 1 = 2 because slide numbering shown is 1-based)
        assertEquals("JabberPoint 1.0 - OU  Slide 2 of 5", frame.getTitle());
    }

    @Test
    void testUpdateTitleWithSlideNumberWhenInvalidSlide() {
        Presentation presentation = mock(Presentation.class);
        when(presentation.getSlideNumber()).thenReturn(-1);
        when(presentation.getSize()).thenReturn(0);

        SlideViewerFrame frame = new SlideViewerFrame("Test", presentation);
        frame.update();

        // Should revert to default title when slide number or size invalid
        assertEquals("JabberPoint 1.0 - OU", frame.getTitle());
    }
}
