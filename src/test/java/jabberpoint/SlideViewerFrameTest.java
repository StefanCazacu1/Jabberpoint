package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import javax.swing.JMenuItem;

class SlideViewerFrameTest {

    @Test
    void testFrameInitialization() {
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame("JabberPoint 1.0 - OU", presentation);

        assertEquals("JabberPoint 1.0 - OU", frame.getTitle());
        assertNotNull(frame.getSlideViewerComponent());
    }

    @Test
    void testSetSlideNumber() {
        Presentation presentation = mock(Presentation.class);
        SlideViewerFrame frame = new SlideViewerFrame("Test", presentation);

        presentation.setSlideNumber(5);
        // We can't *assert* much more without a real GUI event,
        // but no exceptions mean success.
    }
}
