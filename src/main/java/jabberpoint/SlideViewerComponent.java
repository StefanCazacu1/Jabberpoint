package jabberpoint;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The SlideViewerComponent displays the current slide and observes
 * Presentation updates.
 */
public class SlideViewerComponent extends JPanel implements Observer {

    private static final long serialVersionUID = 227L;

    /** The Presentation this component observes and displays. */
    private final Presentation presentationRef;

    /**
     * Constructs a SlideViewerComponent.
     *
     * @param presentation the Presentation to observe and display
     */
    public SlideViewerComponent(final Presentation presentation) {
        this.presentationRef = presentation;
        this.presentationRef.addObserver(this);
    }

    /**
     * Returns the Presentation associated with this component.
     *
     * @return the Presentation
     */
    public Presentation getPresentation() {
        return presentationRef;
    }

    /**
     * Called when the observed Presentation updates.
     */
    @Override
    public void update() {
        repaint();
    }

    /**
     * Paints the current slide.
     *
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (presentationRef.getCurrentSlide() != null) {
            presentationRef.getCurrentSlide().draw(
                    g,
                    this,
                    0,
                    0,
                    1.0f);
        }
    }
}
