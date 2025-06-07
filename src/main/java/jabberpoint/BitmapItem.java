package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents an image item on a slide.
 */
public class BitmapItem extends SlideItem {

	/** Image name/path. */
	private final String name;

	/** Loaded image. */
	private Image bufferedImage;

	/**
	 * Constructs a BitmapItem.
	 *
	 * @param level the slide item level
	 * @param name  the image filename
	 */
	public BitmapItem(final int level, final String name) {
		super(level);
		this.name = name;
		loadImage();
	}

	/**
	 * Loads the image from disk.
	 */
	private void loadImage() {
		try {
			bufferedImage = ImageIO.read(new File(name));
		} catch (IOException e) {
			System.err.println("Error loading image: " + name);
			bufferedImage = null;
		}
	}

	/**
	 * Gets the image name.
	 *
	 * @return the image filename
	 */
	public String getName() {
		return name;
	}

	/**
	 * Draws the image on the slide.
	 *
	 * @param g        the graphics context
	 * @param observer the image observer
	 * @param x        the x-coordinate
	 * @param y        the y-coordinate
	 * @param scale    the scale factor
	 */
	@Override
	public void draw(final Graphics g, final ImageObserver observer, final int x,
			final int y, final float scale) {
		if (bufferedImage == null) {
			loadImage();
		}
		if (bufferedImage != null) {
			g.drawImage(bufferedImage, x, y,
					(int) (bufferedImage.getWidth(observer) * scale),
					(int) (bufferedImage.getHeight(observer) * scale), observer);
		} else {
			g.setColor(Color.RED);
			g.drawRect(x, y, 100, 100);
			g.drawString("Image not found", x + 10, y + 50);
		}
	}

	/**
	 * Gets the bounding box for the image.
	 *
	 * @param g        the graphics context
	 * @param observer the image observer
	 * @param scale    the scale factor
	 * @return the bounding box rectangle, or (0,0,0,0) if image missing
	 */
	@Override
	public Rectangle getBoundingBox(final Graphics g, final ImageObserver observer,
			final float scale) {
		if (bufferedImage == null) {
			loadImage();
		}
		if (bufferedImage != null) {
			int width = (int) (bufferedImage.getWidth(observer) * scale);
			int height = (int) (bufferedImage.getHeight(observer) * scale);
			return new Rectangle(0, 0, width, height);
		}
		return new Rectangle(0, 0, 0, 0);
	}
}
