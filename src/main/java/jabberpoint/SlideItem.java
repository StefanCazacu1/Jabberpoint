package jabberpoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * Abstract class representing an item on a slide.
 */
public abstract class SlideItem {

    /** The hierarchical level of the item. */
    private int level;

    /** The color of the item. */
    private Color color = Color.BLACK;

    /**
     * Constructs a SlideItem.
     * @param levelParam the slide item level
     */
    public SlideItem(final int levelParam) {
        this.level = levelParam;
    }

    /**
     * Gets the item's level.
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the item's level.
     * @param lev the new level
     */
    public void setLevel(final int lev) {
        this.level = lev;
    }

    /**
     * Draws the item on the slide.
     * @param g the Graphics context
     * @param observer the image observer
     * @param x the x coordinate
     * @param y the y coordinate
     * @param scale the scale factor
     */
    public abstract void draw(Graphics g, ImageObserver observer,
            int x, int y, float scale);

    /**
     * Gets the bounding box of the item.
     * @param g the Graphics context
     * @param observer the image observer
     * @param scale the scale factor
     * @return the bounding box Rectangle
     */
    public abstract Rectangle getBoundingBox(Graphics g,
            ImageObserver observer,
            float scale);

    /**
     * Gets the item's color.
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the item's color.
     * @param colorParam the new color
     */
    public void setColor(final Color colorParam) {
        this.color = colorParam;
    }
}
