package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InOrder;

public class SlideTest {

    @Test
    void testInitialSlideState() {
        // Given a new Slide (no title and no items)
        Slide slide = new Slide();
        // Then it should have size 0 and no items
        assertEquals(0, slide.getSize(), "New slide should have 0 items");
        assertTrue(slide.getItems().isEmpty(), "Items vector should be empty");
        // Title should be null initially (not set)
        assertNull(slide.getTitle(), "Title should be null if not set");
        // And getBoundingBox on an empty slide should be 0x0
        Rectangle bbox = slide.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
        assertEquals(0, bbox.width);
        assertEquals(0, bbox.height);
        // Drawing an empty slide should not throw and should not call any draw on items
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);
        slide.draw(g, obs, 50, 50, 1.0f); // Should quietly do nothing
        verifyNoInteractions(g);
    }

    @Test
    void testAddAndGetSlideItems() {
        Slide slide = new Slide();
        // When adding items via append() and addItem()
        SlideItem item1 = new TextItem(1, "Item1");
        SlideItem item2 = new TextItem(2, "Item2");
        slide.append(item1);
        slide.addItem(item2);
        // Then the slide size should reflect the additions
        assertEquals(2, slide.getSize(), "Slide should contain 2 items");
        // And the items should be retrievable in the order they were added
        Vector<SlideItem> items = slide.getItems();
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
    }

    @Test
    void testGetSlideItemInvalidIndex() {
        Slide slide = new Slide();
        slide.append(new TextItem(0, "OnlyItem"));
        // Expect an IndexOutOfBoundsException for negative or too-large index
        Exception ex1 = assertThrows(IndexOutOfBoundsException.class,
                () -> slide.getSlideItem(-1),
                "Negative index should throw");
        assertTrue(ex1.getMessage().contains("Invalid index"));
        Exception ex2 = assertThrows(IndexOutOfBoundsException.class,
                () -> slide.getSlideItem(5),
                "Index beyond last item should throw");
        assertTrue(ex2.getMessage().contains("Invalid index"));
    }

    @Test
    void testSetAndGetTitle() {
        Slide slide = new Slide();
        // When setting a title
        slide.setTitle("Design Patterns");
        // Then getTitle should return the same title
        assertEquals("Design Patterns", slide.getTitle());
        // Setting title to null should be allowed
        slide.setTitle(null);
        assertNull(slide.getTitle(), "Title can be set to null");
    }

    @Test
    void testDrawItemsUpdatesPositions() {
        // Given a slide with two mock SlideItems
        Slide slide = new Slide();
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);

        SlideItem item1 = mock(SlideItem.class);
        SlideItem item2 = mock(SlideItem.class);
        // Define bounding boxes for each item (to control their heights for
        // positioning)
        when(item1.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 100, 40)); // item1 height = 40
        when(item2.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 50, 30)); // item2 height = 30

        slide.append(item1);
        slide.append(item2);

        // When drawing the slide at initial Y=100
        slide.draw(g, obs, 10, 100, 1.0f);

        // Then item1.draw should be called at y=100, and item2.draw at y=100+40 = 140
        InOrder order = inOrder(item1, item2);
        order.verify(item1).draw(g, obs, 10, 100, 1.0f);
        order.verify(item1).getBoundingBox(g, obs, 1.0f);
        order.verify(item2).draw(g, obs, 10, 140, 1.0f);
        order.verify(item2).getBoundingBox(g, obs, 1.0f);
        // No further interactions with items
        order.verifyNoMoreInteractions();
    }

    @Test
    void testGetBoundingBoxAggregatesItemBounds() {
        Slide slide = new Slide();
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);
        // Create two mock items with known bounding boxes
        SlideItem item1 = mock(SlideItem.class);
        SlideItem item2 = mock(SlideItem.class);
        when(item1.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 80, 20)); // width=80, height=20
        when(item2.getBoundingBox(eq(g), eq(obs), eq(1.0f)))
                .thenReturn(new Rectangle(0, 0, 120, 15)); // width=120, height=15
        slide.addItem(item1);
        slide.addItem(item2);
        // When computing the slide's bounding box
        Rectangle totalBox = slide.getBoundingBox(g, obs, 1.0f);
        // Then width should be the max of item widths (120) and height the sum
        // (20+15=35)
        assertEquals(120, totalBox.width, "Total width should be max of item widths");
        assertEquals(35, totalBox.height, "Total height should be sum of item heights");
    }
}
