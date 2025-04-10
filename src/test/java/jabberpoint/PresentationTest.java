package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PresentationTest {

    private Presentation presentation;
    private MockSlideViewerComponent observer;

    // Dummy observer to track updates
    static class MockSlideViewerComponent implements Observer {
        private boolean updated = false;

        @Override
        public void update() {
            updated = true;
        }

        public boolean wasUpdated() {
            return updated;
        }

        public void reset() {
            updated = false;
        }
    }

    @BeforeEach
    void setup() {
        presentation = new Presentation();
        observer = new MockSlideViewerComponent();
        presentation.addObserver(observer);
    }

    @Test
    void presentationShouldStartEmpty() {
        assertEquals(0, presentation.getSize());
    }

    @Test
    void addSlideAndNavigate() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);

        assertEquals(2, presentation.getSize());

        presentation.setSlideNumber(0);
        assertEquals(slide1, presentation.getCurrentSlide());

        presentation.nextSlide();
        assertEquals(slide2, presentation.getCurrentSlide());

        presentation.prevSlide();
        assertEquals(slide1, presentation.getCurrentSlide());
    }

    @Test
    void getSlideOutOfBoundsReturnsNull() {
        assertNull(presentation.getSlide(5));
    }

    @Test
    void clearShouldResetPresentation() {
        presentation.setTitle("Test Title");
        presentation.addSlide(new Slide());

        presentation.clear();

        assertEquals(0, presentation.getSize());
        assertEquals("", presentation.getTitle());
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    void setTitleShouldNotifyObservers() {
        observer.reset();
        presentation.setTitle("New Title");
        assertTrue(observer.wasUpdated());
    }

    @Test
    void setSlideNumberShouldNotifyObservers() {
        observer.reset();
        presentation.setSlideNumber(0);
        assertTrue(observer.wasUpdated());
    }

    @Test
    void removeObserverShouldStopNotifications() {
        presentation.removeObserver(observer);
        observer.reset();
        presentation.setTitle("Another Title");
        assertFalse(observer.wasUpdated());
    }

    @Test
    void unsupportedLoadShouldThrowIOException() {
        IOException thrown = assertThrows(IOException.class, () -> {
            presentation.load("file.ppt"); // Unsupported extension
        });
        assertEquals("Unsupported file type", thrown.getMessage());
    }

    @Test
    void unsupportedSaveShouldThrowIOException() {
        IOException thrown = assertThrows(IOException.class, () -> {
            presentation.save("file.ppt"); // Unsupported extension
        });
        assertEquals("Unsupported file type", thrown.getMessage());
    }

    @Test
    void testNextSlideBoundary() {
        Slide slide1 = new Slide();
        presentation.addSlide(slide1);

        presentation.setSlideNumber(0); // Set the first slide
        presentation.nextSlide(); // Attempt to go to the next slide (should not change)
        assertEquals(0, presentation.getSlideNumber()); // The slide number should remain at 0 (no next slide)
    }

    @Test
    void testPrevSlideBoundary() {
        Slide slide1 = new Slide();
        presentation.addSlide(slide1);

        presentation.setSlideNumber(0); // Set the first slide
        presentation.prevSlide(); // Attempt to go to the previous slide (should not change)
        assertEquals(0, presentation.getSlideNumber()); // The slide number should remain at 0 (no previous slide)
    }

    @Test
    void testGetSlideWithInvalidIndices() {
        // Add some slides to the presentation
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);

        // Test case 1: Index -1 (invalid)
        assertNull(presentation.getSlide(-1), "Should return null for index -1");

        // Test case 2: Index greater than the size of the list
        assertNull(presentation.getSlide(5), "Should return null for index greater than the size of the list");

        // Test case 3: Index equal to the size of the list (out of bounds)
        assertNull(presentation.getSlide(2), "Should return null for index equal to the size of the list");
    }

}
