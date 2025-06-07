package jabberpoint;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The SlideViewerComponent displays the current slide and observes Presentation updates.
 */
public class SlideViewerComponent extends JPanel implements Observer {
	private static final long serialVersionUID = 227L;
	private final Presentation presentation;

	/**
	 * Constructs a SlideViewerComponent.
	 * @param presentation the Presentation to observe and display
	 */
	public SlideViewerComponent(final Presentation presentation) {
		this.presentation = presentation;
		this.presentation.addObserver(this);
	}

	/**
	 * Returns the Presentation associated with this component.
	 * @return the Presentation
	 */
	public Presentation getPresentation() {
		return presentation;
	}

	/**
	 * Called when the observed Presentation updates.
	 */
	@Override
	public void update() {
		repaint();
	}

	/**
	 * Paints the current slide.
	 * @param g the Graphics context
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (presentation.getCurrentSlide() != null) {
			presentation.getCurrentSlide().draw(g, this, 0, 0, 1.0f);
		}
	}
}
