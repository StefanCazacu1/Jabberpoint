package jabberpoint;

import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 * Utility class for showing the About dialog of JabberPoint.
 */
public final class AboutBox {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private AboutBox() {
		// Utility class
	}

	/**
	 * Displays the About dialog.
	 *
	 * @param parent the parent frame for the dialog
	 */
	public static void show(final Frame parent) {
		JOptionPane.showMessageDialog(
				parent,
				"JabberPoint is a primitive slide-show program in Java(tm). It\n"
						+ "is freely copyable as long as you keep this notice and\n"
						+ "the splash screen intact.\n"
						+ "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n"
						+ "Adapted by Gert Florijn (version 1.1) and\n"
						+ "Sylvia Stuurman (version 1.2 and higher) for the Open\n"
						+ "University of the Netherlands, 2002 -- now.\n"
						+ "Author's version available from http://www.darwinsys.com/",
				"About JabberPoint",
				JOptionPane.INFORMATION_MESSAGE
		);
	}
}
