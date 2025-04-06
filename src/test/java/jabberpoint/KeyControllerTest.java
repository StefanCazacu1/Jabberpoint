package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;

class KeyControllerTest {

    private Presentation presentation;
    private KeyController keyController;

    @BeforeEach
    void setUp() {
        presentation = mock(Presentation.class);
        keyController = new KeyController(presentation);
    }

    @Test
    void testNextSlideOnRightArrow() {
        KeyEvent event = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        keyController.keyPressed(event);
        verify(presentation, times(1)).nextSlide();
    }

    @Test
    void testPrevSlideOnLeftArrow() {
        KeyEvent event = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        keyController.keyPressed(event);
        verify(presentation, times(1)).prevSlide();
    }

    @Test
    void testFirstSlideOnHomeKey() {
        KeyEvent event = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_HOME, KeyEvent.CHAR_UNDEFINED);
        keyController.keyPressed(event);
        verify(presentation, times(1)).setSlideNumber(0);
    }

}
