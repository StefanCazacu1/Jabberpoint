package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class StyleTest {

    private Style style;

    @BeforeEach
    void setUp() {
        style = Style.getStyle(0);

    }

    @Test
    void testGetFont() {
        Font font = style.getFont(1.0f);
        assertNotNull(font);
        assertEquals("Helvetica", font.getName());
        assertEquals(24, font.getSize()); // size from constructor
    }

    @Test
    void testDrawString() {
        // Just test that no exception is thrown when drawing
        Frame frame = new Frame();
        frame.addNotify(); // Needed to create a Graphics object
        Image image = frame.createImage(100, 100);
        Graphics g = image.getGraphics();

        assertDoesNotThrow(() -> style.drawString(g, "Test", 10, 10, 1.0f));
    }

    @Test
    void testGetBoundingBox() {
        Frame frame = new Frame();
        frame.addNotify();
        Image image = frame.createImage(100, 100);
        Graphics g = image.getGraphics();

        Rectangle boundingBox = style.getBoundingBox(g, "Test", 1.0f);
        assertNotNull(boundingBox);
        assertTrue(boundingBox.width > 0);
        assertTrue(boundingBox.height > 0);
    }

    @Test
    void testScaleFontSize() {
        float scale = 2.0f;
        Font font = style.getFont(scale);
        assertEquals(48, font.getSize()); // 12 * 2
    }
}
