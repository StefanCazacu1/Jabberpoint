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

    @Test
    void testUpdate() {
        // Add a slide to the presentation before setting the current slide
        Slide slide = new Slide();
        presentation.addSlide(slide); // Add a slide
        presentation.setSlideNumber(0); // Set the first slide as current

        Slide currentSlide = presentation.getCurrentSlide();
        assertNotNull(currentSlide); // Now it should not be null

        // Call update on the viewer component to simulate the update
        viewerComponent.update();

        // Verify that the update led to the current slide being accessible
        assertNotNull(viewerComponent.getPresentation().getCurrentSlide());
    }

    @Test
    void testPaintComponentWithSlide() {
        // Set the current slide using setSlideNumber
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.addSlide(slide);  // Add the slide to the presentation
        presentation.setSlideNumber(0); // Set to the first slide

        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        viewerComponent.paintComponent(graphics);
        graphics.dispose();

        // Add assertions based on what is expected to be drawn on the component
        // This might depend on how the slide draws itself (e.g., its title)
    }

    @Test
    void testGetPresentation() {
        assertNotNull(viewerComponent.getPresentation());
        assertEquals(presentation, viewerComponent.getPresentation());
    }

    @Test
    void testSlideViewerWithNullPresentation() {
        assertThrows(NullPointerException.class, () -> {
            new SlideViewerComponent(null);
        });
    }


}
