package jabberpoint;
//test
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Slide {
	private String title;
	private List<SlideItem> items;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	public Slide() {
		this.items = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void append(SlideItem item) {
		items.add(item);
	}

	public List<SlideItem> getItems() {
		return items;
	}

	public int getSize() {
		return items.size();
	}

	// 🆕 NEW: ADD THIS
	public SlideItem getSlideItem(int index) {
		return items.get(index);
	}

	public void draw(Graphics g, ImageObserver observer, int x, int y, float scale) {
		int cursorY = y;
		for (SlideItem item : items) {
			Style style = Style.getStyle(item.getLevel());
			item.draw(x, cursorY, scale, g, style, observer);
			cursorY += style.getLeading() * scale;
		}
	}
}
