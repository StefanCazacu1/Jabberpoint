package jabberpoint;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * Represents a text item on a slide.
 */
public final class TextItem extends SlideItem {

	/** The text content of this item. */
	private String text;

	/**
	 * Constructs a TextItem.
	 *
	 * @param level the slide item level
	 * @param textContent the text content
	 */
	public TextItem(final int level, final String textContent) {
		super(level);
		this.text = textContent;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param newText the new text
	 */
	public void setText(final String newText) {
		this.text = newText;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	@Override
	public void setLevel(final int level) {
		super.setLevel(level);
	}

	/**
	 * Draws the text item.
	 *
	 * @param g the graphics context
	 * @param observer the image observer
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param scale the scale factor
	 */
	@Override
	public void draw(final Graphics g, final ImageObserver observer,
			final int x, final int y, final float scale) {
		if (g == null) {
			return;
		}
		Style style = Style.getStyle(getLevel());
		Font font = style.getFont(scale);
		g.setFont(font);
		g.setColor(style.getColor());
		int yOffset = y + Math.round(style.getLeading() * scale);
		g.drawString(text != null ? text : "", x, yOffset);
	}

	/**
	 * Gets the bounding box for the text.
	 *
	 * @param g the graphics context
	 * @param observer the image observer
	 * @param scale the scale factor
	 * @return the bounding box rectangle
	 */
	@Override
	public Rectangle getBoundingBox(final Graphics g,
			final ImageObserver observer, final float scale) {
		if (g == null) {
			return new Rectangle(0, 0, 0, 0);
		}
		Style style = Style.getStyle(getLevel());
		Font font = style.getFont(scale);
		FontMetrics metrics = g.getFontMetrics(font);
		int width = metrics.stringWidth(text != null ? text : "");
		int height = metrics.getHeight();
		return new Rectangle(0, 0, width, height);
	}

	/**
	 * Returns the text as a string.
	 *
	 * @return the text
	 */
	@Override
	public String toString() {
		return text != null ? text : "";
	}
}
