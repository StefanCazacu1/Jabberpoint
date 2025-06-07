package jabberpoint;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Controls the menu bar and menu actions for JabberPoint.
 */
public class MenuController extends MenuBar {
	private final Frame parent;
	private final Presentation presentation;

	private final MenuItem nextItem;
	private final MenuItem prevItem;
	private final MenuItem aboutItem;

	public static final String OPEN = "Open";
	public static final String NEW = "New";
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String NEXT = "Next";
	public static final String PREV = "Prev";
	public static final String ABOUT = "About";

	/**
	 * Constructs the menu controller.
	 *
	 * @param parent the parent frame
	 * @param presentation the presentation instance to control
	 */
	public MenuController(final Frame parent, final Presentation presentation) {
		this.parent = parent;
		this.presentation = presentation;

		Menu fileMenu = new Menu("File");
		MenuItem openItem = new MenuItem(OPEN);
		openItem.addActionListener(e -> openFile());
		fileMenu.add(openItem);

		MenuItem saveItem = new MenuItem(SAVE);
		saveItem.addActionListener(e -> saveFile());
		fileMenu.add(saveItem);

		MenuItem exitItem = new MenuItem(EXIT);
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitItem);
		add(fileMenu);

		Menu viewMenu = new Menu("View");

		nextItem = new MenuItem(NEXT);
		nextItem.addActionListener(e -> presentation.nextSlide());
		viewMenu.add(nextItem);

		prevItem = new MenuItem(PREV);
		prevItem.addActionListener(e -> presentation.prevSlide());
		viewMenu.add(prevItem);

		aboutItem = new MenuItem(ABOUT);
		aboutItem.addActionListener(e -> AboutBox.show(parent));
		viewMenu.add(aboutItem);

		add(viewMenu);
	}

	/**
	 * Opens a file dialog and loads a presentation file.
	 */
	public void openFile() {
		FileDialog dialog = new FileDialog(parent, "Open File", FileDialog.LOAD);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String filename = dialog.getDirectory() + dialog.getFile();
			try {
				presentation.load(filename);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, "Error loading file!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Opens a file dialog and saves the presentation to a file.
	 */
	public void saveFile() {
		FileDialog dialog = new FileDialog(parent, "Save File", FileDialog.SAVE);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String filename = dialog.getDirectory() + dialog.getFile();
			try {
				presentation.save(filename);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, "Error saving file!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Returns the "Next" menu item (for testing).
	 * @return the next menu item
	 */
	public MenuItem getNextItem() {
		return nextItem;
	}

	/**
	 * Returns the "Previous" menu item (for testing).
	 * @return the previous menu item
	 */
	public MenuItem getPrevItem() {
		return prevItem;
	}

	/**
	 * Returns the "About" menu item (for testing).
	 * @return the about menu item
	 */
	public MenuItem getAboutItem() {
		return aboutItem;
	}
}
