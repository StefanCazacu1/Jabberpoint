package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Style represents the style for slide items.
 */
public final class Style {
	/** Default font name. */
	public static final String DEFAULT_FONT_NAME = "Helvetica";
	/** Level 0 font size. */
	public static final int LEVEL0_FONT_SIZE = 32;
	/** Level 1 font size. */
	public static final int LEVEL1_FONT_SIZE = 28;
	/** Level 2 font size. */
	public static final int LEVEL2_FONT_SIZE = 24;
	/** Level 3 font size. */
	public static final int LEVEL3_FONT_SIZE = 20;
	/** Default color. */
	public static final Color DEFAULT_COLOR = Color.black;
	/** Default indent. */
	public static final int DEFAULT_INDENT = 20;
	/** Default leading. */
	public static final int DEFAULT_LEADING = 30;

	private final int fontSize;
	private final Color color;
	private final int indent;
	private final int leading;

	/**
	 * Constructs a Style object.
	 * @param fontSize the font size
	 * @param color the color
	 * @param indent the indent
	 * @param leading the leading
	 */
	public Style(final int fontSize, final Color color,
			final int indent, final int leading) {
		this.fontSize = fontSize;
		this.color = color;
		this.indent = indent;
		this.leading = leading;
	}

	/**
	 * Returns a Style object for the given level.
	 * @param level the item level
	 * @return the Style
	 */
	public static Style getStyle(final int level) {
		switch (level) {
			case 0:
				return new Style(LEVEL0_FONT_SIZE, Color.BLACK, 30, 20);
			case 1:
				return new Style(LEVEL1_FONT_SIZE, Color.BLUE, 30, 18);
			case 2:
				return new Style(LEVEL2_FONT_SIZE, Color.DARK_GRAY, 30, 16);
			default:
				return new Style(LEVEL3_FONT_SIZE, Color.GRAY, 30, 14);
		}
	}

	/**
	 * Returns the font size.
	 * @return the font size
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * Returns the Font object for this style.
	 * @param scale the scale factor
	 * @return the Font
	 */
	public Font getFont(final float scale) {
		return new Font(DEFAULT_FONT_NAME, Font.BOLD,
				Math.round(fontSize * scale));
	}

	/**
	 * Returns the indent value.
	 * @return the indent
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * Returns the leading value.
	 * @return the leading
	 */
	public int getLeading() {
		return leading;
	}

	/**
	 * Returns the color.
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets the bounding box width for the string.
	 * @param g the Graphics context
	 * @param text the string
	 * @param scale the scale
	 * @return the width of the string
	 */
	public int getBoundingBox(final Graphics g,
			final String text, final float scale) {
		Font font = getFont(scale);
		g.setFont(font);
		return g.getFontMetrics().stringWidth(text);
	}

	/**
	 * Draws a string with the given style.
	 * @param g the Graphics context
	 * @param text the string
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param scale the scale
	 */
	public void drawString(final Graphics g, final String text, final int x,
			final int y, final float scale) {
		g.setFont(getFont(scale));
		g.setColor(color);
		g.drawString(text, x, y);
	}
}
