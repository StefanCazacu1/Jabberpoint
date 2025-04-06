package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide {
	private String title;
	private Vector<SlideItem> items;

	public Slide() {
		items = new Vector<>();
	}

	// Add a SlideItem to the Slide
	public void append(SlideItem item) {
		items.addElement(item);
	}

	// Alternative name for append
	public void addItem(SlideItem item) {
		items.add(item);
	}

	// Get all SlideItems
	public Vector<SlideItem> getItems() {
		return items;
	}

	// Set the title of the Slide
	public void setTitle(String title) {
		this.title = title;
	}

	// Get the title of the Slide
	public String getTitle() {
		return title;
	}

	// Draw all the items in the slide
	public void draw(Graphics g, ImageObserver observer, int x, int y, float scale) {
		int currentY = y;
		for (SlideItem item : items) {
			item.draw(g, observer, x, currentY, scale);
			Rectangle bounds = item.getBoundingBox(g, observer, scale);
			currentY += bounds.height;
		}
	}

	// --- 🆕 Added methods for Composite Pattern and tests ---

	// Get the number of SlideItems
	public int getSize() {
		return items.size();
	}

	// Get a specific SlideItem by index
	public SlideItem getSlideItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.elementAt(index);
		}
		throw new IndexOutOfBoundsException("Invalid index: " + index);
	}

	// Get the bounding box that wraps all SlideItems
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale) {
		int width = 0;
		int height = 0;
		for (SlideItem item : items) {
			Rectangle itemBox = item.getBoundingBox(g, observer, scale);
			width = Math.max(width, itemBox.width);
			height += itemBox.height;
		}
		return new Rectangle(0, 0, width, height);
	}
}
