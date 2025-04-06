package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*; // <--- this imports both Color, Graphics, Rectangle
import java.awt.image.BufferedImage; // <--- this imports BufferedImage

class StyleTest {

    private Style style;

    @BeforeEach
    void setUp() {
        style = new Style(20, 30, 10, Color.BLACK); // <--- FIXED: added Color
    }

    @Test
    void testGetFont() {
        Font font = style.getFont(1.0f);
        assertNotNull(font);
        assertTrue(
                font.getFamily().equals("Helvetica") || font.getFamily().equals("Dialog"),
                "Expected Helvetica or Dialog but got " + font.getFamily());
        assertEquals(Font.PLAIN, font.getStyle());
    }

    @Test
    void testGetIndent() {
        assertEquals(10, style.getIndent());
    }

    @Test
    void testGetLeading() {
        assertEquals(30, style.getLeading());
    }

    @Test
    void testGetBoundingBox() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        Rectangle rect = style.getBoundingBox(g, "Hello", 1.0f);
        assertNotNull(rect);
        assertTrue(rect.width > 0);
        assertTrue(rect.height > 0);
    }
}
