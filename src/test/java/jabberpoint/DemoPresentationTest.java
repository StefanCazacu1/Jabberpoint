package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {

    @Test
    void testLoadDemoPresentation() {
        Presentation presentation = new Presentation();
        DemoPresentation.loadDemoPresentation(presentation);

        // Ensure that the title of the first slide matches the actual title
        assertEquals("WELCOME TO JABBERPOINT\nTHE WONDER OF GUI TESTING", presentation.getSlides().get(0).getTitle());
    }
}
