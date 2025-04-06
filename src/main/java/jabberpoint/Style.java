package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Style {
	private static Style[] styles;

	static {
		styles = new Style[5];
		styles[0] = new Style(24, 20, 10, Color.red); // Level 0
		styles[1] = new Style(20, 18, 10, Color.blue); // Level 1
		styles[2] = new Style(16, 16, 10, Color.black); // Level 2
		styles[3] = new Style(14, 14, 10, Color.gray); // Level 3
		styles[4] = new Style(12, 12, 10, Color.darkGray); // Level 4
	}

	private int fontSize;
	private int leading;
	private int indent;
	private Color color; // 🆕 ADD THIS

	// 🆕 Updated constructor
	public Style(int fontSize, int leading, int indent, Color color) {
		this.fontSize = fontSize;
		this.leading = leading;
		this.indent = indent;
		this.color = color;
	}

	public static Style getStyle(int level) {
		if (level >= 0 && level < styles.length) {
			return styles[level];
		}
		return styles[styles.length - 1]; // Return last style if level too big
	}

	public Font getFont(float scale) {
		return new Font("Helvetica", Font.PLAIN, Math.round(fontSize * scale));
	}

	public int getIndent() {
		return indent;
	}

	public int getLeading() {
		return leading;
	}

	// 🆕 ADD this method
	public Color getColor() {
		return color;
	}

	public Rectangle getBoundingBox(Graphics g, String text, float scale) {
		Font font = getFont(scale);
		g.setFont(font);
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		return new Rectangle(0, 0, width, height);
	}

	public void drawString(Graphics g, String text, int x, int y, float scale) {
		if (text == null || text.isEmpty())
			return;
		Font font = getFont(scale);
		g.setFont(font);
		g.setColor(color); // 🆕 Set the color when drawing
		g.drawString(text, x + (indent * Math.round(scale)), y);
	}
}
