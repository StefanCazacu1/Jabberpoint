package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlideItemTest {

    // Dummy subclass of SlideItem for testing abstract functionality
    static class DummySlideItem extends SlideItem {
        DummySlideItem(int level) {
            super(level);
        }

        @Override
        public void draw(Graphics g, ImageObserver observer, int x, int y, float scale) {
            // no-op for testing
        }

        @Override
        public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale) {
            // simple fixed-size bounding box for testing
            return new Rectangle(0, 0, 10, 10);
        }
    }

    @Test
    void testLevelAccessors() {
        // Default level set via constructor
        SlideItem item = new DummySlideItem(5);
        assertEquals(5, item.getLevel(), "Level should be set by constructor");
        // Changing the level
        item.setLevel(2);
        assertEquals(2, item.getLevel(), "setLevel should update the level");
        // Setting a negative level (edge case)
        item.setLevel(-1);
        assertEquals(-1, item.getLevel(), "Level can be negative (handled by Style as last style)");
    }

    @Test
    void testColorAccessors() {
        SlideItem item = new DummySlideItem(0);
        // Default color should be black
        assertEquals(Color.BLACK, item.getColor(), "Default color should be BLACK");
        // Setting a new color
        item.setColor(Color.RED);
        assertEquals(Color.RED, item.getColor(), "Color should update to RED");
        // Setting color to null (edge case)
        item.setColor(null);
        assertNull(item.getColor(), "Color can be set to null");
    }
}
