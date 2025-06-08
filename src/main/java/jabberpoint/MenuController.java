package jabberpoint;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controls the menu bar and menu actions for JabberPoint.
 */
public class MenuController extends MenuBar {
    /** Parent frame of the application. */
    private final Frame parent;

    /** The presentation instance this controller manages. */
    private final Presentation presentation;

    /** Menu item to show About information. */
    private final MenuItem aboutItem;

    /** Label for the Open menu item. */
    public static final String OPEN = "Open";

    /** Label for the New Presentation menu item. */
    public static final String NEW = "New Presentation";

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

    /** Label for the Add Slide menu item. */
    public static final String ADD_SLIDE = "Add Slide";

    /** Label for the Remove Slide menu item. */
    public static final String REMOVE_SLIDE = "Remove Slide";

    /** Label for the Add Text Item menu item. */
    public static final String ADD_TEXT_ITEM = "Add Text Item";

    /** Label for the Add Image Item menu item. */
    public static final String ADD_IMAGE_ITEM = "Add Image Item";

    /** Label for the Remove Slide Item menu item. */
    public static final String REMOVE_SLIDE_ITEM = "Remove Slide Item";

    /**
     * Constructs the menu controller.
     *
     * @param parentParam       the parent frame
     * @param presentationParam the presentation instance to control
     */
    public MenuController(final Frame parentParam,
            final Presentation presentationParam) {
        this.parent = parentParam;
        this.presentation = presentationParam;

        // File menu
        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem(NEW);
        newItem.addActionListener(e -> newPresentation());
        fileMenu.add(newItem);

        MenuItem openItem = new MenuItem(OPEN);
        openItem.addActionListener(e -> openFile());
        fileMenu.add(openItem);

        MenuItem saveItem = new MenuItem(SAVE);
        saveItem.addActionListener(e -> saveFile());
        fileMenu.add(saveItem);

        MenuItem exitItem = new MenuItem(EXIT);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        this.add(fileMenu);

        // Edit menu for slide/slide item operations
        Menu editMenu = new Menu("Edit");

        MenuItem addSlideItem = new MenuItem(ADD_SLIDE);
        addSlideItem.addActionListener(e -> addSlide());
        editMenu.add(addSlideItem);

        MenuItem removeSlideItem = new MenuItem(REMOVE_SLIDE);
        removeSlideItem.addActionListener(e -> removeSlide());
        editMenu.add(removeSlideItem);

        MenuItem addTextItem = new MenuItem(ADD_TEXT_ITEM);
        addTextItem.addActionListener(e -> addTextItem());
        editMenu.add(addTextItem);

        MenuItem addImageItem = new MenuItem(ADD_IMAGE_ITEM);
        addImageItem.addActionListener(e -> addImageItem());
        editMenu.add(addImageItem);

        MenuItem removeSlideItemItem = new MenuItem(REMOVE_SLIDE_ITEM);
        removeSlideItemItem.addActionListener(e -> removeSlideItem());
        editMenu.add(removeSlideItemItem);

        this.add(editMenu);

        // Navigation menu
        Menu navigationMenu = new Menu("Navigate");

        MenuItem nextItem = new MenuItem(NEXT);
        nextItem.addActionListener(e -> this.presentation.nextSlide());
        navigationMenu.add(nextItem);

        MenuItem prevItem = new MenuItem(PREV);
        prevItem.addActionListener(e -> this.presentation.prevSlide());
        navigationMenu.add(prevItem);

        this.add(navigationMenu);

        // View menu with only About item
        Menu viewMenu = new Menu("View");

        aboutItem = new MenuItem(ABOUT);
        aboutItem.addActionListener(e -> AboutBox.show(this.parent));
        viewMenu.add(aboutItem);

        this.add(viewMenu);
    }

    private void newPresentation() {
        int result = JOptionPane.showConfirmDialog(parent,
                "Create a new presentation? Unsaved changes will be lost.",
                "New Presentation",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            presentation.clear();
        }
    }

    private void addSlide() {
        String title = JOptionPane.showInputDialog(parent,
                "Enter slide title:",
                "Add Slide",
                JOptionPane.PLAIN_MESSAGE);
        if (title != null && !title.trim().isEmpty()) {
            Slide slide = new Slide();
            slide.setTitle(title.trim());
            presentation.addSlide(slide);
            presentation.setSlideNumber(presentation.getSize() - 1);
        }
    }

    private void removeSlide() {
        int currentIndex = presentation.getSlideNumber();
        if (currentIndex < 0 || currentIndex >= presentation.getSize()) {
            JOptionPane.showMessageDialog(parent,
                    "No slide to remove.",
                    "Remove Slide",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(parent,
                "Remove current slide?",
                "Remove Slide",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Remove slide and update current slide index
            List<Slide> slides = presentation.getSlides();
            slides.remove(currentIndex);
            // Adjust slide number
            if (slides.isEmpty()) {
                presentation.setSlideNumber(-1);
            } else if (currentIndex >= slides.size()) {
                presentation.setSlideNumber(slides.size() - 1);
            } else {
                presentation.setSlideNumber(currentIndex);
            }
        }
    }

    private void addTextItem() {
        int currentIndex = presentation.getSlideNumber();
        if (currentIndex < 0 || currentIndex >= presentation.getSize()) {
            JOptionPane.showMessageDialog(parent,
                    "No slide selected to add a text item.",
                    "Add Text Item",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String text = JOptionPane.showInputDialog(parent,
                "Enter text for new slide item:",
                "Add Text Item",
                JOptionPane.PLAIN_MESSAGE);
        if (text != null && !text.trim().isEmpty()) {
            Slide currentSlide = presentation.getSlide(currentIndex);
            SlideItem textItem = new TextItem(1,
                    text.trim()); // Default level 1
            currentSlide.addItem(textItem);
            // Notify observers after modification
            presentation.notifyPresentationChanged();
        }
    }

    private void addImageItem() {
        int currentIndex = presentation.getSlideNumber();
        if (currentIndex < 0 || currentIndex >= presentation.getSize()) {
            JOptionPane.showMessageDialog(parent,
                    "No slide selected to add an image item.",
                    "Add Image Item",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String imagePath = JOptionPane.showInputDialog(parent,
                "Enter image file path:",
                "Add Image Item",
                JOptionPane.PLAIN_MESSAGE);
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            Slide currentSlide = presentation.getSlide(currentIndex);
            SlideItem imageItem = new BitmapItem(1,
                    imagePath.trim()); // Default level 1
            currentSlide.addItem(imageItem);
            presentation.notifyPresentationChanged();
        }
    }

    private void removeSlideItem() {
        int currentIndex = presentation.getSlideNumber();
        if (currentIndex < 0 || currentIndex >= presentation.getSize()) {
            JOptionPane.showMessageDialog(parent,
                    "No slide selected to remove an item.",
                    "Remove Slide Item",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Slide currentSlide = presentation.getSlide(currentIndex);
        List<SlideItem> items = currentSlide.getSlideItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    "Current slide has no items to remove.",
                    "Remove Slide Item",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] itemDescriptions = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemDescriptions[i] = items.get(i).toString();
        }
        String toRemove = (String) JOptionPane.showInputDialog(parent,
                "Select slide item to remove:",
                "Remove Slide Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemDescriptions,
                itemDescriptions[0]);
        if (toRemove != null) {
            for (SlideItem item : items) {
                if (toRemove.equals(item.toString())) {
                    currentSlide.removeItem(item);
                    presentation.notifyPresentationChanged();
                    break;
                }
            }
        }
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
     * Returns the "About" menu item.
     *
     * @return the about menu item
     */
    public MenuItem getAboutItem() {
        return aboutItem;
    }
}
