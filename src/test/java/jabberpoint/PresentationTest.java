package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

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
}
