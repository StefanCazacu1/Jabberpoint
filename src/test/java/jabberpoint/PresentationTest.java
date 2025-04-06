package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PresentationTest {

    @Test
    void presentationShouldStartEmpty() {
        Presentation presentation = new Presentation();
        assertEquals(0, presentation.getSize());
    }

    @Test
    void addSlideAndNavigate() {
        Presentation presentation = new Presentation();
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.append(slide1);
        presentation.append(slide2);

        assertEquals(2, presentation.getSize());

        presentation.setSlideNumber(0);
        assertEquals(slide1, presentation.getCurrentSlide());

        presentation.nextSlide();
        assertEquals(slide2, presentation.getCurrentSlide());

        presentation.prevSlide();
        assertEquals(slide1, presentation.getCurrentSlide());
    }

    @Test
    void getSlideOutOfBoundsReturnsNull() {
        Presentation presentation = new Presentation();
        assertNull(presentation.getSlide(5));
    }
}
