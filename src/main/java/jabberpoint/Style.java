package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** Style represents the style for slide items. It provides font size, color, indent, and leading values based on item level. */
public final class Style {
    /** Default font name. */
    public static final String DEFAULT_FONT_NAME = "Helvetica";

    /** Level 0 font size. */
    public static final int LEVEL0_FONT_SIZE = 32;
    /** Level 1 font size. */
    public static final int LEVEL1_FONT_SIZE = 28;
    /** Level 2 font size. */
    public static final int LEVEL2_FONT_SIZE = 24;
    /** Level 3 font size. */
    public static final int LEVEL3_FONT_SIZE = 20;

    /** Default color for level 0. */
    public static final Color DEFAULT_COLOR = Color.BLACK;

    /** Default indent value in pixels. */
    public static final int DEFAULT_INDENT = 30;

    /** Default leading (line spacing) for level 0. */
    public static final int DEFAULT_LEADING = 20;

    /** Leading (line spacing) for level 1. */
    public static final int LEVEL1_LEADING = 18;

    /** Leading (line spacing) for level 2. */
    public static final int LEVEL2_LEADING = 16;

    /** Leading (line spacing) for level 3. */
    public static final int LEVEL3_LEADING = 14;

    /** Color for level 1 items. */
    public static final Color LEVEL1_COLOR = Color.BLUE;

    /** Color for level 2 items. */
    public static final Color LEVEL2_COLOR = Color.DARK_GRAY;

    /** Color for level 3 items. */
    public static final Color LEVEL3_COLOR = Color.GRAY;

    /** Font size for the style. */
    private final int fontSize;

    /** Color for the style. */
    private final Color color;

    /** Indentation value for the style. */
    private final int indent;

    /** Leading (line spacing) value for the style. */
    private final int leading;

    /** Constructs a Style object.
     * @param fontSizeParam the font size
     * @param colorParam the color
     * @param indentParam the indent
     * @param leadingParam the leading
     */
    public Style(final int fontSizeParam, final Color colorParam, final int indentParam, final int leadingParam) {
        this.fontSize = fontSizeParam;
        this.color = colorParam;
        this.indent = indentParam;
        this.leading = leadingParam;
    }

    /** Returns a Style object for the given level.
     * @param level the item level
     * @return the Style
     */
    public static Style getStyle(final int level) {
        switch (level) {
            case 0:
                return new Style(LEVEL0_FONT_SIZE, DEFAULT_COLOR, DEFAULT_INDENT, DEFAULT_LEADING);
            case 1:
                return new Style(LEVEL1_FONT_SIZE, LEVEL1_COLOR, DEFAULT_INDENT, LEVEL1_LEADING);
            case 2:
                return new Style(LEVEL2_FONT_SIZE, LEVEL2_COLOR, DEFAULT_INDENT, LEVEL2_LEADING);
            default:
                return new Style(LEVEL3_FONT_SIZE, LEVEL3_COLOR, DEFAULT_INDENT, LEVEL3_LEADING);
        }
    }

    /** Returns the font size.
     * @return the font size
     */
    public int getFontSize() {
        return fontSize;
    }

    /** Returns the Font object for this style.
     * @param scale the scale factor
     * @return the Font
     */
    public Font getFont(final float scale) {
        return new Font(DEFAULT_FONT_NAME, Font.BOLD, Math.round(fontSize * scale));
    }

    /** Returns the indent value.
     * @return the indent
     */
    public int getIndent() {
        return indent;
    }

    /** Returns the leading value.
     * @return the leading
     */
    public int getLeading() {
        return leading;
    }

    /** Returns the color.
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /** Gets the bounding box width for the string.
     * @param g the Graphics context
     * @param text the string
     * @param scale the scale
     * @return the width of the string
     */
    public int getBoundingBox(final Graphics g, final String text, final float scale) {
        Font font = getFont(scale);
        g.setFont(font);
        return g.getFontMetrics().stringWidth(text);
    }

    /** Draws a string with the given style.
     * @param g the Graphics context
     * @param text the string
     * @param x the x coordinate
     * @param y the y coordinate
     * @param scale the scale
     */
    public void drawString(final Graphics g, final String text, final int x, final int y, final float scale) {
        g.setFont(getFont(scale));
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
