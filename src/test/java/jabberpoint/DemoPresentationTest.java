package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DemoPresentation utility class.
 */
class DemoPresentationTest {

    /**
     * Test that the demo presentation loads the expected slide titles and presentation title.
     */
    @Test
    void testLoadDemoPresentation() {
        Presentation presentation = new Presentation();
        DemoPresentation.loadDemoPresentation(presentation);

        // Check the first slide's title matches the one in DemoPresentation.java
        assertEquals("JabberPoint", presentation.getSlides().get(0).getTitle());
        // Also check the presentation title
        assertEquals("Demo Presentation", presentation.getTitle());
        // You can add more asserts for other demo slide content if needed
    }
}
