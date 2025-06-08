package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InOrder;

/**
 * Unit tests for the Slide class and its composite behavior.
 */
public class SlideTest {

    /**
     * Tests initial state of a newly created Slide.
     */
    @Test
    void testInitialSlideState() {
        Slide slide = new Slide();
        assertEquals(0, slide.getSize(), "New slide should have 0 items");
        List<SlideItem> items = slide.getSlideItems();
        assertTrue(items.isEmpty(), "Items list should be empty");
        assertNull(slide.getTitle(), "Title should be null if not set");
        Rectangle bbox = slide.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
        assertEquals(0, bbox.width);
        assertEquals(0, bbox.height);

        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);
        slide.draw(g, obs, 50, 50, 1.0f);
        verifyNoInteractions(g);
    }

    /**
     * Tests adding items and retrieving them.
     */
    @Test
    void testAddAndGetSlideItems() {
        Slide slide = new Slide();
        SlideItem item1 = new TextItem(1, "Item1");
        SlideItem item2 = new TextItem(2, "Item2");
        slide.addItem(item1);
        slide.addItem(item2);

        assertEquals(2, slide.getSize(), "Slide should contain 2 items");
        List<SlideItem> items = slide.getSlideItems();
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
    }

    /**
     * Tests that invalid indices in getSlideItem() throw exceptions.
     */
    @Test
    void testGetSlideItemInvalidIndex() {
        Slide slide = new Slide();
        slide.addItem(new TextItem(0, "OnlyItem"));
        Exception ex1 = assertThrows(IndexOutOfBoundsException.class,
                () -> slide.getSlideItem(-1),
                "Negative index should throw");
        assertTrue(ex1.getMessage().contains("Invalid index"));
        Exception ex2 = assertThrows(IndexOutOfBoundsException.class,
                () -> slide.getSlideItem(5),
                "Index beyond last item should throw");
        assertTrue(ex2.getMessage().contains("Invalid index"));
    }

    /**
     * Tests setting and getting the slide title.
     */
    @Test
    void testSetAndGetTitle() {
        Slide slide = new Slide();
        slide.setTitle("Design Patterns");
        assertEquals("Design Patterns", slide.getTitle());
        slide.setTitle(null);
        assertNull(slide.getTitle(), "Title can be set to null");
    }

    /**
     * Tests that drawing items calls draw() and adjusts y-positions properly.
     */
    @Test
    void testDrawItemsUpdatesPositions() {
        Slide slide = new Slide();
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);

        SlideItem item1 = mock(SlideItem.class);
        SlideItem item2 = mock(SlideItem.class);

        when(item1.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 100, 40));
        when(item2.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 50, 30));

        slide.addItem(item1);
        slide.addItem(item2);

        slide.draw(g, obs, 10, 100, 1.0f);

        InOrder order = inOrder(item1, item2);
        order.verify(item1).draw(g, obs, 10, 100, 1.0f);
        order.verify(item1).getBoundingBox(g, obs, 1.0f);
        order.verify(item2).draw(g, obs, 10, 140, 1.0f);
        order.verify(item2).getBoundingBox(g, obs, 1.0f);
        order.verifyNoMoreInteractions();
    }

    /**
     * Tests that getBoundingBox() aggregates widths and heights correctly.
     */
    @Test
    void testGetBoundingBoxAggregatesItemBounds() {
        Slide slide = new Slide();
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);
        SlideItem item1 = mock(SlideItem.class);
        SlideItem item2 = mock(SlideItem.class);

        when(item1.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 80, 20));
        when(item2.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 120, 15));

        slide.addItem(item1);
        slide.addItem(item2);

        Rectangle totalBox = slide.getBoundingBox(g, obs, 1.0f);
        assertEquals(120, totalBox.width, "Total width should be max of item widths");
        assertEquals(35, totalBox.height, "Total height should be sum of item heights");
    }
}
