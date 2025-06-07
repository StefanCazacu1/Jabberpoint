package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {

    @Test
    void testLoadDemoPresentation() {
        Presentation presentation = new Presentation();
        DemoPresentation.loadDemoPresentation(presentation);

        // Match the first slide's actual title from DemoPresentation.java
        assertEquals("JabberPoint", presentation.getSlides().get(0).getTitle());
        // Optionally: test the presentation's title
        assertEquals("Demo Presentation", presentation.getTitle());
        // You could add more checks here if you want
    }
}
