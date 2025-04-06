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
        TextItem textItem = new TextItem(1, "Hello");
        assertEquals("Hello", textItem.getText());
        assertEquals(1, textItem.getLevel());

        textItem.setText("New Text");
        textItem.setLevel(3);
        assertEquals("New Text", textItem.getText());
        assertEquals(3, textItem.getLevel());

        textItem.setText(null);
        assertNull(textItem.getText(), "Text can be set to null (although drawing will handle it as an empty string)");
    }

    @Test
    void testDrawDoesNothingIfGraphicsNull() {
        TextItem textItem = new TextItem(2, "Test");
        textItem.draw(null, mock(ImageObserver.class), 0, 0, 1.0f);
    }

    @Test
    void testDrawSetsFontColorAndDrawsString() {
        // Given a TextItem and a Graphics context
        TextItem textItem = new TextItem(1, "Hello"); // level 1 uses Style level 1 (Blue color, 28pt font, leading 18)
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);

        // When drawing the text item at (x=10, y=5) with scale 1.0
        textItem.draw(g, obs, 10, 5, 1.0f);

        // Then Graphics should be set with the proper font and color, and drawString
        // called with text
        // Style level 1 -> font size 28, plain, color BLUE, leading 18
        verify(g).setFont(argThat(
                font -> font.getSize() == 28 && "Helvetica".equals(font.getName()) && font.getStyle() == Font.PLAIN));
        verify(g).setColor(Color.blue);

        // The y-coordinate for drawString should be y + (leading * scale) = 5 + 18 = 23
        verify(g).drawString("Hello", 10, 23);
    }

    @Test
    void testDrawAppliesScaleFactor() {
        // Given a TextItem at level 1 and a Graphics context
        TextItem textItem = new TextItem(1, "Hi"); // level 1, base font size 28pt, leading 18
        Graphics g = mock(Graphics.class);

        // When drawing with scale 2.0
        textItem.draw(g, mock(ImageObserver.class), 0, 0, 2.0f);

        // Then the font size should double (28pt base -> 56pt) and y offset double (18
        // -> 36)
        verify(g).setFont(argThat(font -> font.getSize() == 56)); // Font size should be 56 (28 * 2)
        // The drawString y position should be 0 + 36 = 36 for leading=18 scaled by 2
        verify(g).drawString("Hi", 0, 36);
    }

    @Test
    void testGetBoundingBoxWithGraphics() {
        TextItem textItem = new TextItem(2, "Hello");
        Graphics g = mock(Graphics.class);
        ImageObserver obs = mock(ImageObserver.class);

        FontMetrics fm = mock(FontMetrics.class);
        when(g.getFontMetrics(any(Font.class))).thenReturn(fm);
        when(fm.stringWidth("Hello")).thenReturn(50);
        when(fm.getHeight()).thenReturn(10);

        Rectangle box = textItem.getBoundingBox(g, obs, 1.0f);
        assertEquals(50, box.width);
        assertEquals(10, box.height);
    }

    @Test
    void testGetBoundingBoxAppliesScale() {
        TextItem textItem = new TextItem(2, "Hi");
        Graphics g = mock(Graphics.class);

        when(g.getFontMetrics(any(Font.class))).thenAnswer(invocation -> {
            Font font = invocation.getArgument(0);
            FontMetrics fm = mock(FontMetrics.class);
            String text = "Hi";
            when(fm.stringWidth(text)).thenReturn(font.getSize() * text.length());
            when(fm.getHeight()).thenReturn(font.getSize());
            return fm;
        });

        Rectangle box1 = textItem.getBoundingBox(g, mock(ImageObserver.class), 1.0f);
        Rectangle box2 = textItem.getBoundingBox(g, mock(ImageObserver.class), 2.0f);

        assertEquals(box1.width * 2, box2.width);
        assertEquals(box1.height * 2, box2.height);
    }

    @Test
    void testGetBoundingBoxNullGraphics() {
        TextItem textItem = new TextItem(0, "Test");
        Rectangle box = textItem.getBoundingBox(null, mock(ImageObserver.class), 1.0f);
        assertEquals(0, box.width);
        assertEquals(0, box.height);
    }

    @Test
    void testGetBoundingBoxWithEmptyAndNullText() {
        TextItem emptyTextItem = new TextItem(1, "");
        Graphics g = mock(Graphics.class);
        FontMetrics fm = mock(FontMetrics.class);
        when(g.getFontMetrics(any(Font.class))).thenReturn(fm);
        when(fm.stringWidth("")).thenReturn(0);
        when(fm.getHeight()).thenReturn(15);

        Rectangle emptyBox = emptyTextItem.getBoundingBox(g, mock(ImageObserver.class), 1.0f);
        assertEquals(0, emptyBox.width);
        assertEquals(15, emptyBox.height);

        TextItem nullTextItem = new TextItem(1, null);
        when(fm.stringWidth(null)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, () -> nullTextItem.getBoundingBox(g, null, 1.0f));
    }
}
