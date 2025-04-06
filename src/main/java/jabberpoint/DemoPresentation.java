package jabberpoint;

import java.awt.Image;
import java.io.IOException;

public class DemoPresentation {

	public static void loadDemoPresentation(Presentation presentation) {
		presentation.setTitle("Demo Presentation");

		// Slide 1: Welcome Slide with background image
		Slide slide1 = new Slide();
		slide1.setTitle("WELCOME TO JABBERPOINT\nTHE WONDER OF GUI TESTING");

		// Add the title
		slide1.addItem(new TextItem(1, "WELCOME TO JABBERPOINT"));
		slide1.addItem(new TextItem(2, "THE WONDER OF GUI TESTING"));

		// Add the subtitle
		slide1.addItem(new TextItem(3, "I must be crazy to use mockito so simulate all the interface to test it"));

		// Add the footer
		slide1.addItem(new TextItem(4, "BY STEFAN AND IARINA"));

		presentation.addSlide(slide1);

		// Slide 2: Features
		Slide slide2 = new Slide();
		slide2.setTitle("Features");
		slide2.addItem(new TextItem(1, "Multiple file formats (XML/JSON)"));
		slide2.addItem(new TextItem(2, "Observer pattern for dynamic updates"));
		slide2.addItem(new TextItem(3, "Strategy pattern for flexible file IO"));
		presentation.addSlide(slide2);

		// Slide 3: Footer and "Next Slide" action
		Slide slide3 = new Slide();
		slide3.setTitle("Go Next Slide");
		slide3.addItem(new TextItem(1, "Go to the next slide"));
		slide3.addItem(new TextItem(2, "BY STEFAN AND IARINA"));
		presentation.addSlide(slide3);
	}
}
