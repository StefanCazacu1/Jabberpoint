package jabberpoint;

/**
 * Provides a demo presentation with sample slides.
 * Utility class: not meant to be instantiated.
 */
public final class DemoPresentation {

	// Private constructor to prevent instantiation
	private DemoPresentation() {
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Loads a demo presentation into the provided Presentation object.
	 *
	 * @param presentation the Presentation to load slides into
	 */
	public static void loadDemoPresentation(final Presentation presentation) {
		if (presentation == null) {
			return;
		}
		presentation.setTitle("Demo Presentation");

		// Level constants for clarity
		final int TITLE_LEVEL = 0;
		final int SUBTITLE_LEVEL = 1;
		final int CONTENT_LEVEL = 2;

		Slide slide1 = new Slide();
		slide1.setTitle("JabberPoint");
		slide1.append(new TextItem(TITLE_LEVEL, "The Java Presentation Tool"));
		slide1.append(new TextItem(SUBTITLE_LEVEL, "by Stef Cazacu & Co."));
		presentation.addSlide(slide1);

		Slide slide2 = new Slide();
		slide2.setTitle("Features");
		slide2.append(new TextItem(CONTENT_LEVEL, "Composite Pattern"));
		slide2.append(new TextItem(CONTENT_LEVEL, "Observer Pattern"));
		slide2.append(new TextItem(CONTENT_LEVEL, "Strategy Pattern"));
		slide2.append(new TextItem(CONTENT_LEVEL, "Unit Testing with JUnit"));
		presentation.addSlide(slide2);

		// Add more demo slides as needed...
	}
}
