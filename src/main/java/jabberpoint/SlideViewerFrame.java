package jabberpoint;

import java.awt.*;

/**
 * The main application window (Frame) that shows the presentation.
 */
public class SlideViewerFrame extends Frame
{

    private static final long serialVersionUID = 1L;

    /**
     * Default window title.
     */
    private static final String JABTITLE = "JabberPoint 1.0 - OU";

    /**
     * Default window width in pixels.
     */
    private static final int DEFAULT_WIDTH = 1024;

    /**
     * Default window height in pixels.
     */
    private static final int DEFAULT_HEIGHT = 768;

    /**
     * The presentation to display.
     */
    private final Presentation presentation;

    /**
     * The component that renders the slides.
     */
    private final SlideViewerComponent slideViewerComponent;

    /**
     * Constructs a SlideViewerFrame.
     *
     * @param title        the window title
     * @param presentation the Presentation to display
     */
    public SlideViewerFrame(final String title, final Presentation presentation)
    {
        super(title);
        this.presentation = presentation;

        // Add the SlideViewerComponent which observes Presentation
        slideViewerComponent = new SlideViewerComponent(presentation);
        presentation.addObserver(slideViewerComponent);

        // Add key controller and menu controller
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));

        setTitle(JABTITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);

        // When closing the window, exit the application
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(final java.awt.event.WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });

        add(slideViewerComponent, BorderLayout.CENTER);
    }

    /**
     * Returns the SlideViewerComponent.
     *
     * @return the SlideViewerComponent
     */
    public SlideViewerComponent getSlideViewerComponent()
    {
        return slideViewerComponent;
    }

}
