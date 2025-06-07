package jabberpoint;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import static org.junit.jupiter.api.Assertions.*;

class StyleTest {

    @Test
    void testStyleConstructorAndGetters() {
        int fontSize = 32;
        int indent = 30;
        int leading = 20;
        Color color = Color.RED;
        Style style = new Style(fontSize, color, indent, leading);

        assertEquals(fontSize, style.getFontSize());
        assertEquals(leading, style.getLeading());
        assertEquals(indent, style.getIndent());
        assertEquals(color, style.getColor());
    }

    @Test
    void testGetFont() {
        Style style = new Style(32, Color.BLACK, 30, 20);
        Font font = style.getFont(1.0f);
        assertNotNull(font);
        assertEquals(32, font.getSize());
        // Optionally check font name and style
        assertEquals("Helvetica", font.getName());
        assertEquals(Font.BOLD, font.getStyle());
    }

    @Test
    void testGetBoundingBox() {
        Style style = new Style(32, Color.BLUE, 30, 20);
        Graphics g = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
        int width = style.getBoundingBox(g, "Hello", 1.0f);
        assertTrue(width > 0);
    }

    @Test
    void testDrawString() {
        Style style = new Style(32, Color.BLUE, 30, 20);
        Graphics g = new java.awt.image.BufferedImage(100, 100, java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
        // Should not throw:
        assertDoesNotThrow(() -> style.drawString(g, "Hello", 10, 10, 1.0f));
    }

    @Test
    void testStaticGetStyle() {
        Style s0 = Style.getStyle(0);
        assertEquals(32, s0.getFontSize());
        assertEquals(Color.BLACK, s0.getColor());

        Style s1 = Style.getStyle(1);
        assertEquals(28, s1.getFontSize());
        assertEquals(Color.BLUE, s1.getColor());

        Style s2 = Style.getStyle(2);
        assertEquals(24, s2.getFontSize());
        assertEquals(Color.DARK_GRAY, s2.getColor());

        Style s3 = Style.getStyle(99);
        assertEquals(20, s3.getFontSize());
        assertEquals(Color.GRAY, s3.getColor());
    }
}
