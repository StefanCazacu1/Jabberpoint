package jabberpoint;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The SlideViewerComponent displays the current slide and observes
 * Presentation.
 */
public class SlideViewerComponent extends JPanel implements Observer {
	private static final long serialVersionUID = 227L;
	private final Presentation presentation;

	public SlideViewerComponent(Presentation presentation) {
		this.presentation = presentation;
		this.presentation.addObserver(this);
	}

	public Presentation getPresentation() {
		return presentation;
	}

	@Override
	public void update() {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (presentation.getCurrentSlide() != null) {
			presentation.getCurrentSlide().draw(g, this, 0, 0, 1.0f);
		}
	}

}
