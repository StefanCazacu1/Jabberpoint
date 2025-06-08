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

    // Padding for slide Y coordinate
    private static final int SLIDE_Y_OFFSET = 10;

    // Alpha value for translucent background behind slide number
    private static final int BG_ALPHA = 150;

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
     * Always repaints on update to ensure UI reflects all changes.
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
                    SLIDE_Y_OFFSET,
                    1.0f);
        }

        int slideNumber = presentationRef.getSlideNumber();
        int totalSlides = presentationRef.getSize();

        String slideNumText = "Slide " + (slideNumber + 1) + " / "
                + totalSlides;

        // Padding from edges
        int padding = 0;

        // Get font metrics to calculate text size
        int textWidth = g.getFontMetrics().stringWidth(slideNumText);
        int textHeight = g.getFontMetrics().getHeight();

        // Calculate x and y so text appears in bottom-right corner
        int x = getWidth() - textWidth - padding;
        int y = getHeight() - padding;

        // Draw translucent background rectangle behind text
        g.setColor(new java.awt.Color(0, 0, 0, BG_ALPHA)); // semi-transparent black
        g.fillRect(x - padding / 2,
                y - g.getFontMetrics().getAscent(),
                textWidth + padding,
                textHeight + padding / 2);

        // Draw the slide number text in white
        g.setColor(java.awt.Color.WHITE);
        g.drawString(slideNumText, x, y);
    }
}
