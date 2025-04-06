package jabberpoint;

import javax.swing.*;
import java.awt.*;

/**
 * The main application window (Frame) that shows the presentation.
 */
public class SlideViewerFrame extends Frame {
	private static final String JABTITLE = "JabberPoint 1.0 - OU";

	private final Presentation presentation;
	private final SlideViewerComponent slideViewerComponent;

	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		this.presentation = presentation;

		// Add the SlideViewerComponent which observes Presentation
		slideViewerComponent = new SlideViewerComponent(presentation);
		presentation.addObserver(slideViewerComponent);

		// Add key controller and menu controller
		addKeyListener(new KeyController(presentation));
		setMenuBar(new MenuController(this, presentation));

		setTitle(JABTITLE);
		setSize(1024, 768);
		setVisible(true);

		// When closing the window, exit the application
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		add(slideViewerComponent, BorderLayout.CENTER);
	}

	public SlideViewerComponent getSlideViewerComponent() {
		return slideViewerComponent;
	}

	public Presentation getPresentation() {
		return this.presentation;
	}
}
