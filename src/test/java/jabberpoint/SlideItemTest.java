package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the abstract SlideItem class.
 * Uses a dummy concrete subclass for testing.
 */
public class SlideItemTest {

    /** Dummy subclass of SlideItem for testing abstract methods. */
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
            // Return fixed size for testing
            return new Rectangle(0, 0, 10, 10);
        }
    }

    /** Tests the getLevel() and setLevel() methods. */
    @Test
    void testLevelAccessors() {
        SlideItem item = new DummySlideItem(5);
        assertEquals(5, item.getLevel(), "Level should be set by constructor");

        item.setLevel(2);
        assertEquals(2, item.getLevel(), "setLevel should update the level");

        // Edge case: negative level
        item.setLevel(-1);
        assertEquals(-1, item.getLevel(), "Level can be negative (handled by Style)");
    }

    /** Tests the getColor() and setColor() methods. */
    @Test
    void testColorAccessors() {
        SlideItem item = new DummySlideItem(0);

        // Default color should be BLACK
        assertEquals(Color.BLACK, item.getColor(), "Default color should be BLACK");

        item.setColor(Color.RED);
        assertEquals(Color.RED, item.getColor(), "Color should update to RED");

        // Edge case: setting color to null
        item.setColor(null);
        assertNull(item.getColor(), "Color can be set to null");
    }
}
