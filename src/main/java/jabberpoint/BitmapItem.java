package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Represents an image item on a slide. */
public final class BitmapItem extends SlideItem {

    /** Width of error rectangle drawn when image fails to load. */
    private static final int ERROR_RECT_WIDTH = 100;

    /** Height of error rectangle drawn when image fails to load. */
    private static final int ERROR_RECT_HEIGHT = 100;

    /** X offset for error text inside the rectangle. */
    private static final int ERROR_TEXT_X_OFFSET = 10;

    /** Y offset for error text inside the rectangle. */
    private static final int ERROR_TEXT_Y_OFFSET = 50;

    /** Image filename. */
    private final String imageName;

    /** Loaded image. */
    private Image bufferedImage;

    /**
     * Constructs a BitmapItem.
     *
     * @param level the slide item level
     * @param imageNameParam the image filename
     */
    public BitmapItem(final int level, final String imageNameParam) {
        super(level);
        this.imageName = imageNameParam;
        loadImage();
    }

    /** Loads the image from disk. */
    private void loadImage() {
        try {
            bufferedImage = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.err.println("Error loading image: " + imageName);
            bufferedImage = null;
        }
    }

    /**
     * Gets the image filename.
     *
     * @return the image filename
     */
    public String getName() {
        return imageName;
    }

    /**
     * Draws the image on the slide.
     *
     * @param g the graphics context
     * @param observer the image observer
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param scale the scale factor
     */
    @Override
    public void draw(final Graphics g,
            final ImageObserver observer,
            final int x,
            final int y, final float scale) {
        if (bufferedImage == null) {
            loadImage();
        }
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, x, y,
                    (int) (bufferedImage.getWidth(observer) * scale),
                    (int) (bufferedImage.getHeight(observer) * scale),
                    observer);
        } else {
            g.setColor(Color.RED);
            g.drawRect(x, y, ERROR_RECT_WIDTH, ERROR_RECT_HEIGHT);
            g.drawString("Image not found",
                    x + ERROR_TEXT_X_OFFSET,
                    y + ERROR_TEXT_Y_OFFSET);
        }
    }

    /**
     * Gets the bounding box for the image.
     *
     * @param g the graphics context
     * @param observer the image observer
     * @param scale the scale factor
     * @return the bounding box rectangle, or (0,0,0,0) if image missing
     */
    @Override
    public Rectangle getBoundingBox(final Graphics g,
            final ImageObserver observer,
            final float scale) {
        if (bufferedImage == null) {
            loadImage();
        }
        if (bufferedImage != null) {
            int width = (int)
                    (bufferedImage.getWidth(observer) * scale);
            int height = (int)
                    (bufferedImage.getHeight(observer) * scale);
            return new Rectangle(0, 0, width, height);
        }
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public String toString() {
        return getName();
    }

}
