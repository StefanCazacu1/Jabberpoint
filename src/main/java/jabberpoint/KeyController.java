package jabberpoint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyController extends KeyAdapter {
	private final Presentation presentation;

	public KeyController(Presentation presentation) {
		this.presentation = presentation;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_RIGHT:
				presentation.nextSlide();
				break;
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_LEFT:
				presentation.prevSlide();
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
			case KeyEvent.VK_HOME:
				presentation.setSlideNumber(0);
				break;
			default:
				// Do nothing
		}
	}

}
