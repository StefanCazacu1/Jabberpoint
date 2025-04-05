package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SlideTest {

    @Test
    void testAddSlideItem() {
        Slide slide = new Slide();
        TextItem item = new TextItem(1, "Test item");
        slide.append(item);

        assertEquals(1, slide.getSize());
        assertEquals(item, slide.getSlideItem(0));
    }

    @Test
    void testTitle() {
        Slide slide = new Slide();
        slide.setTitle("My Slide Title");

        assertEquals("My Slide Title", slide.getTitle());
    }
}
