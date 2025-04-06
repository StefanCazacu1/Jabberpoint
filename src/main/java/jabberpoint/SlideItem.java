package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public abstract class SlideItem {
	protected int level;
	private Color color = Color.BLACK; // Default color is black
	
	public SlideItem(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int lev) {
		level = lev;
	}

	public abstract void draw(Graphics g, ImageObserver observer, int x, int y, float scale);

	public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale);
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
