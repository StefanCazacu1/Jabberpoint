package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.HashMap;

public class Style implements Serializable {
	private static final long serialVersionUID = 227L;

	private static final HashMap<Integer, Style> styles = new HashMap<>();

	private final int indent;
	private final Color color;
	private final Font font;
	private final int fontSize;
	private final int leading;

	static {
		styles.put(0, new Style(0, Color.red, 48, 20)); // Title
		styles.put(1, new Style(20, Color.blue, 40, 10)); // First level
		styles.put(2, new Style(50, Color.black, 36, 10)); // Second level
		styles.put(3, new Style(70, Color.black, 30, 10)); // Third level
		styles.put(4, new Style(90, Color.black, 24, 10)); // Fourth level
	}

	public Style(int indent, Color color, int fontSize, int leading) {
		this.indent = indent;
		this.color = color;
		this.fontSize = fontSize;
		this.leading = leading;
		this.font = new Font("Helvetica", Font.BOLD, fontSize);
	}

	public static Style getStyle(int level) {
		Style s = styles.get(level);
		if (s == null) {
			return styles.get(4); // fallback
		}
		return s;
	}

	public int getIndent() {
		return indent;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

	public int getLeading() {
		return leading;
	}
}
