package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PresentationTest {

    private Presentation presentation;
    private MockSlideViewerComponent observer;

    // Dummy observer to track notifications from Presentation
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
            presentation.load("file.ppt"); // Unsupported file type
        });
        assertEquals("Unsupported file type", thrown.getMessage());
    }

    @Test
    void unsupportedSaveShouldThrowIOException() {
        IOException thrown = assertThrows(IOException.class, () -> {
            presentation.save("file.ppt"); // Unsupported file type
        });
        assertEquals("Unsupported file type", thrown.getMessage());
    }

    @Test
    void testNextSlideBoundary() {
        Slide slide1 = new Slide();
        presentation.addSlide(slide1);

        presentation.setSlideNumber(0); // Set first slide
        presentation.nextSlide(); // Should not advance
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testPrevSlideBoundary() {
        Slide slide1 = new Slide();
        presentation.addSlide(slide1);

        presentation.setSlideNumber(0); // Set first slide
        presentation.prevSlide(); // Should not go negative
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testGetSlideWithInvalidIndices() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);

        assertNull(presentation.getSlide(-1), "Should return null for index -1");
        assertNull(presentation.getSlide(5), "Should return null for index greater than size");
        assertNull(presentation.getSlide(2), "Should return null for index equal to size");
    }

    // New tests for notification optimization and observer notifications

    @Test
    void setTitleSameValueDoesNotNotify() {
        presentation.setTitle("Title");
        observer.reset();
        presentation.setTitle("Title"); // Same title again
        assertFalse(observer.wasUpdated());
    }

    @Test
    void setSlideNumberSameValueDoesNotNotify() {
        presentation.setSlideNumber(1);
        observer.reset();
        presentation.setSlideNumber(1); // Same slide number again
        assertFalse(observer.wasUpdated());
    }

    @Test
    void clearShouldNotifyObservers() {
        observer.reset();
        presentation.clear();
        assertTrue(observer.wasUpdated());
    }

    @Test
    void addSlideShouldNotifyObservers() {
        observer.reset();
        presentation.addSlide(new Slide());
        assertTrue(observer.wasUpdated());
    }
}
