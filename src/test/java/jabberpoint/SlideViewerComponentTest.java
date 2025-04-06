package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;

class SlideViewerComponentTest {

    private SlideViewerComponent viewerComponent;
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        viewerComponent = new SlideViewerComponent(presentation);
    }

    @Test
    void testUpdatePresentation() {
        Presentation newPresentation = new Presentation();
        viewerComponent = new SlideViewerComponent(newPresentation);
        assertEquals(newPresentation, viewerComponent.getPresentation());
    }

    @Test
    void testGetPreferredSize() {
        assertNotNull(viewerComponent.getPreferredSize());
    }

    @Test
    void testPaintComponentWithoutSlide() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        viewerComponent.paintComponent(graphics);
        graphics.dispose();
        // No exception = success
    }
}
