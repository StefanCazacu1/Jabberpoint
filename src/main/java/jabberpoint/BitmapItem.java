package jabberpoint;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class BitmapItem extends SlideItem {
	private String imageName;
	private Image image;

	public BitmapItem(int level, String imageName) {
		super(level);
		this.imageName = imageName;
		loadImage();
	}

	private void loadImage() {
		if (imageName != null) {
			image = new ImageIcon(imageName).getImage();
		}
	}

	public String getName() {
		return imageName;
	}

	@Override
	public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
		if (image == null) {
			return;
		}
		int width = (int) (image.getWidth(observer) * scale);
		int height = (int) (image.getHeight(observer) * scale);
		g.drawImage(image, x, y, width, height, observer);
	}

	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
		if (image == null) {
			return new Rectangle();
		}
		int width = (int) (image.getWidth(observer) * scale);
		int height = (int) (image.getHeight(observer) * scale);
		return new Rectangle(0, 0, width, height);
	}
}
