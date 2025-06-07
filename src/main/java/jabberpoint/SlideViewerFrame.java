package jabberpoint;

import java.awt.BorderLayout;
import java.awt.Frame;

/**
 * The main application window (Frame) that shows the presentation.
 */
public class SlideViewerFrame extends Frame {
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
     * Returns the SlideViewerComponent.
     * @return the SlideViewerComponent
     */
    public SlideViewerComponent getSlideViewerComponent() {
        return slideViewerComponent;
    }
}
