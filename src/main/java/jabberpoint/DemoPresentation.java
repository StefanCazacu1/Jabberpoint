package jabberpoint;

public class DemoPresentation {

	public static void loadDemoPresentation(Presentation presentation) {
		presentation.setTitle("Demo Presentation");

		Slide slide1 = new Slide();
		slide1.setTitle("Welcome to JabberPoint");
		slide1.addItem(new TextItem(1, "The Java Presentation Tool"));
		slide1.addItem(new TextItem(2, "Copyright (c) 2025"));
		presentation.addSlide(slide1);

		Slide slide2 = new Slide();
		slide2.setTitle("Features");
		slide2.addItem(new TextItem(1, "Multiple file formats (XML/JSON)"));
		slide2.addItem(new TextItem(2, "Observer pattern for dynamic updates"));
		slide2.addItem(new TextItem(2, "Strategy pattern for flexible file IO"));
		presentation.addSlide(slide2);
	}
}
