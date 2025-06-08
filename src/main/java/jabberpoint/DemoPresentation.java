package jabberpoint;

/** Utility class providing a demo presentation with sample slides. */
public final class DemoPresentation {
    /** Prevents instantiation. */
    private DemoPresentation() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Loads a demo presentation into the provided Presentation object.
     *
     * @param presentation the Presentation to load slides into
     */
    public static void loadDemoPresentation(
            final Presentation presentation) {
        if (presentation == null) {
            return;
        }
        presentation.setTitle("Demo Presentation");

        // Style level constants (local variables)
        final int titleLevel = 0;
        final int subtitleLevel = 1;
        final int contentLevel = 2;

        Slide slide1 = new Slide();
        slide1.setTitle("JabberPoint");
        slide1.addItem(new TextItem(titleLevel,
                "The Java Presentation Tool"));
        slide1.addItem(new TextItem(subtitleLevel,
                "by Stef Cazacu & Co."));
        presentation.addSlide(slide1);

        Slide slide2 = new Slide();
        slide2.setTitle("Features");
        slide2.addItem(new TextItem(contentLevel,
                "Composite Pattern"));
        slide2.addItem(new TextItem(contentLevel,
                "Observer Pattern"));
        slide2.addItem(new TextItem(contentLevel,
                "Strategy Pattern"));
        slide2.addItem(new TextItem(contentLevel,
                "Unit Testing with JUnit"));
        presentation.addSlide(slide2);

        // Add more demo slides as needed...
    }
}
