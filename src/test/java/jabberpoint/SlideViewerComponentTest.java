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
    void testConstructorRejectsNull() {
        assertThrows(NullPointerException.class, () -> new SlideViewerComponent(null));
    }

    @Test
    void testUpdatePresentation() {
        Presentation newPresentation = new Presentation();
        SlideViewerComponent newViewer = new SlideViewerComponent(newPresentation);
        assertEquals(newPresentation, newViewer.getPresentation());
    }

    @Test
    void testGetPreferredSizeNotNull() {
        assertNotNull(viewerComponent.getPreferredSize());
    }

    @Test
    void testPaintComponentWithoutSlide() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        viewerComponent.paintComponent(graphics);
        graphics.dispose();
        // Success means no exceptions
    }

    @Test
    void testUpdateTriggersRepaint() {
        // Just call update and confirm no errors
        viewerComponent.update();
    }

    @Test
    void testPaintComponentWithSlide() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.addSlide(slide);
        presentation.setSlideNumber(0);

        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        viewerComponent.paintComponent(graphics);
        graphics.dispose();
    }

    @Test
    void testGetPresentationReturnsCorrectInstance() {
        assertEquals(presentation, viewerComponent.getPresentation());
    }
}
