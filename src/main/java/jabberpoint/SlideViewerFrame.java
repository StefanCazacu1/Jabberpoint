package jabberpoint;

import java.awt.BorderLayout;
import java.awt.Frame;

/**
 * The main application window (Frame) that shows the presentation.
 */
public class SlideViewerFrame extends Frame implements Observer {
    private static final long serialVersionUID = 1L;

    /** Default window title. */
    private static final String JABTITLE = "JabberPoint 1.0 - OU";

    /** Default window width in pixels. */
    private static final int DEFAULT_WIDTH = 1024;

    /** Default window height in pixels. */
    private static final int DEFAULT_HEIGHT = 768;

    /** The presentation to display. */
    private final Presentation presentation;

    /** The component that renders the slides. */
    private final SlideViewerComponent slideViewerComponent;

    /**
     * Constructs a SlideViewerFrame.
     * @param title the window title
     * @param presentationParam the Presentation to display
     */
    public SlideViewerFrame(final String title,
            final Presentation presentationParam) {
        super(title);
        this.presentation = presentationParam;

        // Register this frame as an observer to presentation
        this.presentation.addObserver(this);

        slideViewerComponent = new SlideViewerComponent(this.presentation);
        this.presentation.addObserver(slideViewerComponent);

        addKeyListener(new KeyController(this.presentation));
        setMenuBar(new MenuController(this, this.presentation));

        setTitle(JABTITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(
                    final java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        add(slideViewerComponent, BorderLayout.CENTER);
    }

    /**
     * Called when the observed Presentation updates.
     * Updates the window title with current slide info.
     */
    @Override
    public void update() {
        int slideNumber = presentation.getSlideNumber();
        int totalSlides = presentation.getSize();
        if (slideNumber >= 0 && totalSlides > 0) {
            updateTitleWithSlideNumber(slideNumber, totalSlides);
        } else {
            setTitle(JABTITLE);
        }
    }

    /**
     * Updates the window title with slide number and total slides.
     * @param slideNumber final current slide index
     * @param totalSlides final total number of slides
     */
    public void updateTitleWithSlideNumber(final int slideNumber,
            final int totalSlides) {
        setTitle("JabberPoint 1.0 - OU  Slide " + (slideNumber + 1)
                + " of " + totalSlides);
    }

    /**
     * Returns the SlideViewerComponent.
     * @return the SlideViewerComponent
     */
    public SlideViewerComponent getSlideViewerComponent() {
        return slideViewerComponent;
    }
}
