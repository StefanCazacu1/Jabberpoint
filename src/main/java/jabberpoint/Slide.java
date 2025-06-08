package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a slide in the JabberPoint presentation.
 * Acts as a composite SlideItem containing multiple SlideItems.
 */
public class Slide extends SlideItem {

    /** The title of the Slide. */
    private String slideTitle;

    /** List of child SlideItems contained in this Slide. */
    private final List<SlideItem> items;

    /**
     * Constructs a new Slide with level 0.
     */
    public Slide() {
        super(0); // level 0 for Slide
        items = new ArrayList<>();
    }

    /**
     * Adds a SlideItem child to this Slide.
     * @param item the SlideItem to add
     */
    public void addItem(final SlideItem item) {
        items.add(item);
    }

    /**
     * Removes a SlideItem child from this Slide.
     * @param item the SlideItem to remove
     */
    public void removeItem(final SlideItem item) {
        items.remove(item);
    }

    /**
     * Returns the number of SlideItem children in this Slide.
     * @return the number of items
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Returns a SlideItem child at the specified index.
     * @param index the index of the SlideItem to return
     * @return the SlideItem at the given index
     */
    public SlideItem getItem(final int index) {
        return items.get(index);
    }

    /**
     * Gets all SlideItems as a List.
     * @return a new List containing all SlideItems
     */
    public List<SlideItem> getSlideItems() {
        return new ArrayList<>(items);
    }

    /**
     * Returns the number of SlideItems in this Slide.
     * @return the number of items
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Returns the SlideItem at the specified index.
     * Throws IndexOutOfBoundsException if the index is invalid.
     * @param index the index of the SlideItem to return
     * @return the SlideItem at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public SlideItem getSlideItem(final int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return items.get(index);
    }

    /**
     * Sets the title of this Slide.
     * @param title the new title
     */
    public void setTitle(final String title) {
        this.slideTitle = title;
    }

    /**
     * Gets the title of this Slide.
     * @return the slide title
     */
    public String getTitle() {
        return slideTitle;
    }

    /**
     * Draws all child SlideItems on the slide.
     * Each item is drawn vertically stacked with spacing based on bounding boxes.
     *
     * @param g the Graphics context to draw on
     * @param observer the ImageObserver to notify for image updates
     * @param x the x-coordinate to start drawing
     * @param y the y-coordinate to start drawing
     * @param scale the scaling factor for drawing size
     */
    @Override
    public void draw(final Graphics g,
            final ImageObserver observer,
            final int x,
            final int y,
            final float scale) {
        int currentY = y;
        for (SlideItem item : items) {
            item.draw(g, observer, x, currentY, scale);
            Rectangle bounds = item.getBoundingBox(g, observer, scale);
            currentY += bounds.height;
        }
    }

    /**
     * Calculates the bounding box that encloses all child SlideItems.
     *
     * @param g the Graphics context
     * @param observer the ImageObserver to notify for image updates
     * @param scale the scaling factor for size
     * @return a Rectangle representing the bounding box around all items
     */
    @Override
    public Rectangle getBoundingBox(final Graphics g,
            final ImageObserver observer,
            final float scale) {
        int width = 0;
        int height = 0;
        for (SlideItem item : items) {
            Rectangle itemBox = item.getBoundingBox(g, observer, scale);
            width = Math.max(width, itemBox.width);
            height += itemBox.height;
        }
        return new Rectangle(0, 0, width, height);
    }
}
