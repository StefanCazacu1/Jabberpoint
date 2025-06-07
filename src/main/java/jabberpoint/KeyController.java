package jabberpoint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * Handles keyboard input for slide navigation.
 */
public class KeyController extends KeyAdapter {

    /**
     * The Presentation this controller manipulates.
     */
    private final Presentation presentation;

    /**
     * Constructs a KeyController for the given Presentation.
     *
     * @param presentationParam the Presentation to control
     */
    public KeyController(final Presentation presentationParam) {
        this.presentation = presentationParam;
    }

    /**
     * Handles key press events for slide navigation.
     *
     * @param keyEvent the KeyEvent to process
     */
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_RIGHT:
                presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
                presentation.prevSlide();
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_HOME:
                presentation.setSlideNumber(0);
                break;
            default:
                // Do nothing
        }
    }
}
