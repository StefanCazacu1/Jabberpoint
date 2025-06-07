package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

/**
 * Unit tests for the BitmapItem class.
 */
public class BitmapItemTest {

    /**
     * Tests that getName() returns the correct image file name.
     */
    @Test
    void testGetNameProperty() {
        BitmapItem item = new BitmapItem(0, "example.png");
        assertEquals("example.png", item.getName());
    }

    /**
     * Tests image loading failure: bounding box should be 0,0 and no image drawn.
     */
    @Test
    void testImageLoadingFailure() {
        BitmapItem item = new BitmapItem(1, "nonexistent_file.jpg");
        Rectangle box = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
        assertEquals(0, box.width);
        assertEquals(0, box.height);

        Graphics g = mock(Graphics.class);
        item.draw(g, mock(ImageObserver.class), 10, 10, 1.0f);
        verify(g, never()).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), any(ImageObserver.class));
    }

    /**
     * Tests getBoundingBox with a loaded image at different scales.
     */
    @Test
    void testGetBoundingBoxWithImageLoaded() {
        BufferedImage dummyImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        try (MockedStatic<ImageIO> imageIOStatic = mockStatic(ImageIO.class)) {
            imageIOStatic.when(() -> ImageIO.read(any(java.io.File.class))).thenReturn(dummyImage);
            BitmapItem item = new BitmapItem(2, "dummy.png");
            Rectangle box1 = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
            Rectangle box2 = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 2.0f);
            assertEquals(100, box1.width);
            assertEquals(50, box1.height);
            assertEquals(200, box2.width);
            assertEquals(100, box2.height);
        }
    }

    /**
     * Tests drawing with a loaded image: should call drawImage with correct scaling.
     */
    @Test
    void testDrawWithImageLoaded() {
        BufferedImage dummyImage = new BufferedImage(40, 30, BufferedImage.TYPE_INT_ARGB);
        try (MockedStatic<ImageIO> imageIOStatic = mockStatic(ImageIO.class)) {
            imageIOStatic.when(() -> ImageIO.read(any(java.io.File.class))).thenReturn(dummyImage);
            BitmapItem item = new BitmapItem(0, "dummy2.png");
            Graphics g = mock(Graphics.class);
            ImageObserver obs = mock(ImageObserver.class);
            item.draw(g, obs, 5, 5, 2.0f);
            verify(g).drawImage(same(dummyImage), eq(5), eq(5), eq(80), eq(60), eq(obs));
            verifyNoMoreInteractions(g);
        }
    }
}
