package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {

    @Test
    void testLoadDemoPresentation() {
        Presentation presentation = new Presentation();
        DemoPresentation.loadDemoPresentation(presentation);

        // Test the presentation title
        assertEquals("Demo Presentation", presentation.getTitle());

        // Test the number of slides
        assertEquals(2, presentation.getSlides().size());

        // Test the first slide title
        Slide firstSlide = presentation.getSlides().get(0);
        assertEquals("Welcome to JabberPoint", firstSlide.getTitle());

        // Test if the first slide has correct items
        assertEquals(2, firstSlide.getItems().size());

        // Test the second slide title
        Slide secondSlide = presentation.getSlides().get(1);
        assertEquals("Features", secondSlide.getTitle());

        // Test if the second slide has the correct number of items
        assertEquals(3, secondSlide.getItems().size());
    }
}
