package jabberpoint;

import java.io.IOException;

/**
 * Main entry point for the JabberPoint presentation application.
 */
public final class JabberPoint
{

    /**
     * Error message for IO errors.
     */
    public static final String IO_ERR = "IO Error: ";
    /**
     * Error message for loading errors.
     */
    public static final String LOAD_ERR = "Error while loading presentation: ";

    /**
     * Private constructor to prevent instantiation.
     */
    private JabberPoint()
    {
        // Utility class
    }

    /**
     * Main method to start the application.
     *
     * @param argv command-line arguments
     */
    public static void main(final String[] argv)
    {
        Presentation presentation = new Presentation();
        new SlideViewerFrame("JabberPoint 2.0", presentation);

        try
        {
            if (argv.length == 0)
            {
                // If no file provided, load demo presentation
                DemoPresentation.loadDemoPresentation(presentation);
            }
            else
            {
                // If a file is provided, load it
                presentation.load(argv[0]);
            }
        }
        catch (IOException ex)
        {
            System.err.println(IO_ERR + ex.getMessage());
        }
        presentation.setSlideNumber(0);
    }

}
