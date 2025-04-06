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
			case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> presentation.nextSlide();
			case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, KeyEvent.VK_LEFT -> presentation.prevSlide();
			case KeyEvent.VK_Q -> System.exit(0);
			case KeyEvent.VK_HOME -> presentation.setSlideNumber(0);

			default -> {
				/* Do nothing */ }
		}
	}
}
