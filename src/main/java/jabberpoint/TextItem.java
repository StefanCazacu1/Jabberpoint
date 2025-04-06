package jabberpoint;

import java.awt.*;
import java.awt.image.ImageObserver;

public class TextItem extends SlideItem {
	private String text;

	public TextItem(int level, String text) {
		super(level);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) { // 🆕 Add this
		this.text = text;
	}

	public void setLevel(int level) { // 🆕 Add this
		super.level = level;
	}

	@Override
	public void draw(Graphics g, ImageObserver observer, int x, int y, float scale) {
		if (g == null)
			return;
		Style style = Style.getStyle(getLevel());
		Font font = style.getFont(scale);
		g.setFont(font);
		g.setColor(style.getColor());
		g.drawString(text, x, y + (int) (style.getLeading() * scale));
	}

	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale) {
		if (g == null)
			return new Rectangle(0, 0, 0, 0);
		Style style = Style.getStyle(getLevel());
		Font font = style.getFont(scale);
		FontMetrics metrics = g.getFontMetrics(font);
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		return new Rectangle(0, 0, width, height);
	}
}
