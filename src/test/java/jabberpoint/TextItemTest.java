package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

public class TextItemTest {

    @Test
    void testTextAndLevelAccessors() {
        // Given a TextItem with initial level and text
        TextItem textItem = new TextItem(1, "Hello");
        assertEquals("Hello", textItem.getText());
        assertEquals(1, textItem.getLevel());
        // When changing the text and level
        textItem.setText("New Text");
        textItem.setLevel(3);
        // Then the changes should be reflected
        assertEquals("New Text", textItem.getText());
        assertEquals(3, textItem.getLevel());
        // Setting text to null should be allowed
        textItem.setText(null);
        assertNull(textItem.getText(), "Text can be set to null (although drawing will handle it as an empty string)");
    }

    @Test
    void testDrawDoesNothingIfGraphicsNull() {
        TextItem textItem = new TextItem(2, "Test");
        // Calling draw with a null Graphics should simply return without exceptions
        textItem.draw(null, mock(ImageObserver.class), 0, 0, 1.0f);
        // If no exception is thrown, the test passes for this scenario
    }

    @Test
    void testDrawSetsFontColorAndDrawsString() {
        // Given a TextItem and a Graphics context
        TextItem textItem = new TextItem(1, "Hello"); // level 1 uses Style level 1 (Blue color, 20pt font, leading 18)
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);

        // When drawing the text item at (x=10,y=5) with scale 1.0
        textItem.draw(g, obs, 10, 5, 1.0f);

        // Then Graphics should be set with the proper font and color, and drawString
        // called with text
        // Style level 1 -> font size 20, plain, color BLUE, leading 18
        verify(g).setFont(argThat(
                font -> font.getSize() == 20 && "Helvetica".equals(font.getName()) && font.getStyle() == Font.PLAIN));
        verify(g).setColor(Color.blue);
        // The y-coordinate for drawString should be y + (leading * scale) = 5 + 18 = 23
        verify(g).drawString("Hello", 10, 23);
    }

    @Test
    void testDrawAppliesScaleFactor() {
        // Given a TextItem at level 1 and a Graphics context
        TextItem textItem = new TextItem(1, "Hi");
        Graphics g = mock(Graphics.class);
        // When drawing with scale 2.0
        textItem.draw(g, mock(ImageObserver.class), 0, 0, 2.0f);
        // Then the font size should double (20pt base -> 40pt) and y offset double (18
        // -> 36)
        verify(g).setFont(argThat(font -> font.getSize() == 40));
        // The drawString y position should be 0 + 36 = 36 for leading=18 scaled by 2
        verify(g).drawString("Hi", 0, 36);
    }

    @Test
    void testGetBoundingBoxWithGraphics() {
        TextItem textItem = new TextItem(2, "Hello");
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);
        // Stub FontMetrics to return known width/height for "Hello"
        FontMetrics fm = mock(FontMetrics.class);
        when(g.getFontMetrics(any(Font.class))).thenReturn(fm);
        when(fm.stringWidth("Hello")).thenReturn(50);
        when(fm.getHeight()).thenReturn(10);
        // When getting bounding box (scale 1.0)
        Rectangle box = textItem.getBoundingBox(g, obs, 1.0f);
        // Then width and height should match the stubbed FontMetrics values
        assertEquals(50, box.width);
        assertEquals(10, box.height);
    }

    @Test
    void testGetBoundingBoxAppliesScale() {
        TextItem textItem = new TextItem(2, "Hi"); // level 2 base font 16pt, leading 16
        Graphics g = mock(Graphics.class);
        // Use an Answer to simulate FontMetrics that depends on font size
        when(g.getFontMetrics(any(Font.class))).thenAnswer(invocation -> {
            Font font = invocation.getArgument(0);
            FontMetrics fm = mock(FontMetrics.class);
            // Simulate string width proportional to font size and text length
            String text = "Hi";
            when(fm.stringWidth(text)).thenReturn(font.getSize() * text.length());
            // Simulate height equal to font size (for simplicity)
            when(fm.getHeight()).thenReturn(font.getSize());
            return fm;
        });
        // When getting bounding box at different scales
        Rectangle box1 = textItem.getBoundingBox(g, mock(ImageObserver.class), 1.0f);
        Rectangle box2 = textItem.getBoundingBox(g, mock(ImageObserver.class), 2.0f);
        // Then box2 should be approximately twice the size of box1 in both dimensions
        assertEquals(box1.width * 2, box2.width, "Width should scale with font size");
        assertEquals(box1.height * 2, box2.height, "Height should scale with font size");
    }

    @Test
    void testGetBoundingBoxNullGraphics() {
        TextItem textItem = new TextItem(0, "Test");
        // When Graphics is null, getBoundingBox should return a 0,0,0,0 rectangle
        Rectangle box = textItem.getBoundingBox(null, mock(ImageObserver.class), 1.0f);
        assertEquals(0, box.width);
        assertEquals(0, box.height);
    }

    @Test
    void testGetBoundingBoxWithEmptyAndNullText() {
        // Empty text case
        TextItem emptyTextItem = new TextItem(1, "");
        Graphics g = mock(Graphics.class);
        FontMetrics fm = mock(FontMetrics.class);
        when(g.getFontMetrics(any(Font.class))).thenReturn(fm);
        when(fm.stringWidth("")).thenReturn(0);
        when(fm.getHeight()).thenReturn(15);
        Rectangle emptyBox = emptyTextItem.getBoundingBox(g, mock(ImageObserver.class), 1.0f);
        assertEquals(0, emptyBox.width, "Empty string should have 0 width");
        assertEquals(15, emptyBox.height, "Height should still be font height for empty text");

        // Null text case (edge scenario: expect a NullPointerException when measuring
        // text)
        TextItem nullTextItem = new TextItem(1, null);
        when(fm.stringWidth(null)).thenThrow(new NullPointerException());
        // Using assertThrows to check that a NullPointerException occurs during
        // bounding box calculation
        assertThrows(NullPointerException.class, () -> nullTextItem.getBoundingBox(g, null, 1.0f),
                "Null text should cause a NullPointerException in getBoundingBox");
    }
}
