package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BitmapItem extends SlideItem {
	private String imageName;
	private Image image;

	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		loadImage();
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			System.err.println("Error loading image: " + imageName);
		}
	}

	@Override
	public void draw(Graphics g, ImageObserver observer, int x, int y, float scale) {
		if (image != null) {
			Style style = Style.getStyle(getLevel());
			g.drawImage(image, x, y, (int) (image.getWidth(observer) * scale),
					(int) (image.getHeight(observer) * scale), observer);
		}
	}

	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale) {
		if (image == null)
			return new Rectangle(0, 0, 0, 0);
		return new Rectangle(0, 0,
				(int) (image.getWidth(observer) * scale),
				(int) (image.getHeight(observer) * scale));
	}

	public String getName() {
		return imageName;
	}
}
