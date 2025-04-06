package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.font.TextLayout;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class TextItem extends SlideItem {
	private String text;

	public TextItem(int level, String text) {
		super(level);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
		if (text == null || text.isEmpty()) {
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(style.getFont(scale));
		g2d.setColor(style.getColor());

		Point pen = new Point(x + (int) (style.getIndent() * scale), y + (int) (style.getLeading() * scale));
		TextLayout layout = new TextLayout(text, style.getFont(scale), g2d.getFontRenderContext());
		layout.draw(g2d, pen.x, pen.y);
	}

	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
		Graphics2D g2d = (Graphics2D) g;
		TextLayout layout = new TextLayout(text, style.getFont(scale), g2d.getFontRenderContext());
		Rectangle bounds = layout.getPixelBounds(null, 0, 0);
		return new Rectangle(0, 0, bounds.width, bounds.height);
	}
}
