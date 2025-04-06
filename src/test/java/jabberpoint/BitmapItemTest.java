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

public class BitmapItemTest {

    @Test
    void testGetNameProperty() {
        BitmapItem item = new BitmapItem(0, "example.png");
        assertEquals("example.png", item.getName());
    }

    @Test
    void testImageLoadingFailure() {
        // Given an invalid image path (simulate file not found)
        BitmapItem item = new BitmapItem(1, "nonexistent_file.jpg");
        // The image should be null internally, so:
        // getBoundingBox should return 0,0 dimensions
        Rectangle box = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
        assertEquals(0, box.width);
        assertEquals(0, box.height);
        // draw() should not attempt to draw anything (no exceptions, no drawImage
        // calls)
        Graphics g = mock(Graphics.class);
        item.draw(g, mock(ImageObserver.class), 10, 10, 1.0f);
        verify(g, never()).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), any(ImageObserver.class));
    }

    @Test
    void testGetBoundingBoxWithImageLoaded() {
        // Simulate a successful image load with a dummy BufferedImage (100x50)
        BufferedImage dummyImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        try (MockedStatic<ImageIO> imageIOStatic = mockStatic(ImageIO.class)) {
            imageIOStatic.when(() -> ImageIO.read(any(java.io.File.class))).thenReturn(dummyImage);
            // When creating a BitmapItem, the static ImageIO.read returns dummyImage
            BitmapItem item = new BitmapItem(2, "dummy.png");
            // The image should now be loaded (dummyImage). Test getBoundingBox at different
            // scales:
            Rectangle box1 = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 1.0f);
            Rectangle box2 = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), 2.0f);
            // At scale 1.0, bounding box should match image size (100x50)
            assertEquals(100, box1.width);
            assertEquals(50, box1.height);
            // At scale 2.0, bounding box should double the image size (200x100)
            assertEquals(200, box2.width);
            assertEquals(100, box2.height);
        }
    }

    @Test
    void testDrawWithImageLoaded() {
        BufferedImage dummyImage = new BufferedImage(40, 30, BufferedImage.TYPE_INT_ARGB);
        try (MockedStatic<ImageIO> imageIOStatic = mockStatic(ImageIO.class)) {
            imageIOStatic.when(() -> ImageIO.read(any(java.io.File.class))).thenReturn(dummyImage);
            BitmapItem item = new BitmapItem(0, "dummy2.png");
            // Prepare a Graphics mock to verify drawing
            Graphics g = mock(Graphics.class);
            ImageObserver obs = mock(ImageObserver.class);
            // When drawing the item at (x=5, y=5) with scale 2.0
            item.draw(g, obs, 5, 5, 2.0f);
            // Then it should call Graphics.drawImage with the scaled width and height
            // (40*2=80, 30*2=60)
            verify(g).drawImage(same(dummyImage), eq(5), eq(5), eq(80), eq(60), eq(obs));
            verifyNoMoreInteractions(g);
        }
    }
}
