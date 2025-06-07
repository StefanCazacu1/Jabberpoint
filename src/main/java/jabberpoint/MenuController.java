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
    /** Parent frame of the application. */
    private final Frame parent;

    /** The presentation instance this controller manages. */
    private final Presentation presentation;

    /** Menu item for going to the next slide. */
    private final MenuItem nextItem;

    /** Menu item for going to the previous slide. */
    private final MenuItem prevItem;

    /** Menu item to show About information. */
    private final MenuItem aboutItem;

    /** Label for the Open menu item. */
    public static final String OPEN = "Open";

    /** Label for the New menu item. */
    public static final String NEW = "New";

    /** Label for the Save menu item. */
    public static final String SAVE = "Save";

    /** Label for the Exit menu item. */
    public static final String EXIT = "Exit";

    /** Label for the Next menu item. */
    public static final String NEXT = "Next";

    /** Label for the Previous menu item. */
    public static final String PREV = "Prev";

    /** Label for the About menu item. */
    public static final String ABOUT = "About";

    /**
     * Constructs the menu controller.
     *
     * @param parent       the parent frame
     * @param presentation the presentation instance to control
     */
    public MenuController(final Frame parent,
            final Presentation presentation) {
        this.parent = parent;
        this.presentation = presentation;

        Menu fileMenu = new Menu("File");

        MenuItem openItem = new MenuItem(OPEN);
        openItem.addActionListener(e -> this.openFile());
        fileMenu.add(openItem);

        MenuItem saveItem = new MenuItem(SAVE);
        saveItem.addActionListener(e -> this.saveFile());
        fileMenu.add(saveItem);

        MenuItem exitItem = new MenuItem(EXIT);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        this.add(fileMenu);

        Menu viewMenu = new Menu("View");

        nextItem = new MenuItem(NEXT);
        nextItem.addActionListener(e -> this.presentation.nextSlide());
        viewMenu.add(nextItem);

        prevItem = new MenuItem(PREV);
        prevItem.addActionListener(e -> this.presentation.prevSlide());
        viewMenu.add(prevItem);

        aboutItem = new MenuItem(ABOUT);
        aboutItem.addActionListener(e -> AboutBox.show(this.parent));
        viewMenu.add(aboutItem);

        this.add(viewMenu);
    }

    /**
     * Opens a file dialog and loads a presentation file.
     * Shows an error dialog if loading fails.
     */
    public void openFile() {
        FileDialog dialog = new FileDialog(this.parent,
                "Open File",
                FileDialog.LOAD);
        dialog.setVisible(true);
        if (dialog.getFile() != null) {
            String filename = dialog.getDirectory() + dialog.getFile();
            try {
                this.presentation.load(filename);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this.parent,
                        "Error loading file!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opens a file dialog and saves the presentation to a file.
     * Shows an error dialog if saving fails.
     */
    public void saveFile() {
        FileDialog dialog = new FileDialog(this.parent,
                "Save File",
                FileDialog.SAVE);
        dialog.setVisible(true);
        if (dialog.getFile() != null) {
            String filename = dialog.getDirectory() + dialog.getFile();
            try {
                this.presentation.save(filename);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this.parent,
                        "Error saving file!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Returns the "Next" menu item.
     *
     * @return the next slide menu item
     */
    public MenuItem getNextItem() {
        return nextItem;
    }

    /**
     * Returns the "Previous" menu item.
     *
     * @return the previous slide menu item
     */
    public MenuItem getPrevItem() {
        return prevItem;
    }

    /**
     * Returns the "About" menu item.
     *
     * @return the about menu item
     */
    public MenuItem getAboutItem() {
        return aboutItem;
    }
}
