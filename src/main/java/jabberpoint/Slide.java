package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.Vector;

/**
 * Represents a slide in the JabberPoint presentation.
 */
public class Slide {

	/** The slide title. */
	private String slideTitle;

	/** List of slide items. */
	private final Vector<SlideItem> items;

	/**
	 * Constructs a new Slide.
	 */
	public Slide() {
		items = new Vector<>();
	}

	/**
	 * Appends a SlideItem to the slide.
	 * @param item the item to add
	 */
	public void append(final SlideItem item) {
		items.addElement(item);
	}

	/**
	 * Alternative name for append.
	 * @param item the item to add
	 */
	public void addItem(final SlideItem item) {
		items.add(item);
	}

	/**
	 * Gets all SlideItems as a Vector.
	 * @return the items
	 */
	public Vector<SlideItem> getItems() {
		return items;
	}

	/**
	 * Sets the title of the Slide.
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.slideTitle = title;
	}

	/**
	 * Gets the title of the Slide.
	 * @return the slide title
	 */
	public String getTitle() {
		return slideTitle;
	}

	/**
	 * Draws all items in the slide.
	 * @param g the Graphics context
	 * @param observer the image observer
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param scale the scale factor
	 */
	public void draw(final Graphics g, final ImageObserver observer, final int x,
			final int y, final float scale) {
		int currentY = y;
		for (SlideItem item : items) {
			item.draw(g, observer, x, currentY, scale);
			Rectangle bounds = item.getBoundingBox(g, observer, scale);
			currentY += bounds.height;
		}
	}

	/**
	 * Gets the number of SlideItems.
	 * @return the number of items
	 */
	public int getSize() {
		return items.size();
	}

	/**
	 * Gets a specific SlideItem by index.
	 * @param index the item index
	 * @return the SlideItem at the given index
	 * @throws IndexOutOfBoundsException if index invalid
	 */
	public SlideItem getSlideItem(final int index) {
		if (index >= 0 && index < items.size()) {
			return items.elementAt(index);
		}
		throw new IndexOutOfBoundsException("Invalid index: " + index);
	}

	/**
	 * Gets the bounding box that wraps all SlideItems.
	 * @param g the Graphics context
	 * @param observer the image observer
	 * @param scale the scale
	 * @return the bounding box Rectangle
	 */
	public Rectangle getBoundingBox(final Graphics g, final ImageObserver observer, final float scale) {
		int width = 0;
		int height = 0;
		for (SlideItem item : items) {
			Rectangle itemBox = item.getBoundingBox(g, observer, scale);
			width = Math.max(width, itemBox.width);
			height += itemBox.height;
		}
		return new Rectangle(0, 0, width, height);
	}

	/**
	 * Gets all SlideItems as a List.
	 * @return the list of SlideItems
	 */
	public List<SlideItem> getSlideItems() {
		return items;
	}
}
