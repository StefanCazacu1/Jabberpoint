package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PresentationTest {

    @Test
    void testAddSlide() {
        Presentation presentation = new Presentation();
        Slide slide = new Slide();
        presentation.append(slide);

        assertEquals(1, presentation.getSize());
        assertSame(slide, presentation.getSlide(0));
    }

    @Test
    void testSlideNavigation() {
        Presentation presentation = new Presentation();
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);

        presentation.setSlideNumber(0);
        assertEquals(slide1, presentation.getCurrentSlide());

        presentation.nextSlide();
        assertEquals(slide2, presentation.getCurrentSlide());
    }
}
