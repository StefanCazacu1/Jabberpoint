package jabberpoint;

import java.io.IOException;

public class JabberPoint {

	public static final String IO_ERR = "IO Error: ";
	public static final String LOAD_ERR = "Error while loading presentation: ";

	public static void main(String[] argv) {
		Presentation presentation = new Presentation();
		new SlideViewerFrame("JabberPoint 2.0", presentation);

		try {
			if (argv.length == 0) {
				// If no file provided, load demo presentation
				DemoPresentation.loadDemoPresentation(presentation);
			} else {
				// If a file is provided, load it
				presentation.load(argv[0]);
			}
		} catch (IOException ex) {
			System.err.println(IO_ERR + ex.getMessage());
		}
		presentation.setSlideNumber(0);
	}
}
