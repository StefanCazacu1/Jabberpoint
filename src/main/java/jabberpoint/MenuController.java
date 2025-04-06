package jabberpoint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

public class MenuController extends MenuBar {
	private final Frame parent;
	private final Presentation presentation;

	public MenuItem nextItem; 
	public MenuItem prevItem; 
	public MenuItem aboutItem;

	public static final String OPEN = "Open";
	public static final String NEW = "New";
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String NEXT = "Next";
	public static final String PREV = "Prev";
	public static final String ABOUT = "About";

	public MenuController(Frame parent, Presentation presentation) {
		this.parent = parent;
		this.presentation = presentation;

		Menu fileMenu = new Menu("File");
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(e -> openFile());
		fileMenu.add(openItem);

		MenuItem saveItem = new MenuItem("Save");
		saveItem.addActionListener(e -> saveFile());
		fileMenu.add(saveItem);

		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitItem);
		add(fileMenu);

		Menu viewMenu = new Menu("View");
		MenuItem nextItem = new MenuItem("Next Slide");
		nextItem.addActionListener(e -> presentation.nextSlide());
		viewMenu.add(nextItem);

		MenuItem prevItem = new MenuItem("Previous Slide");
		prevItem.addActionListener(e -> presentation.prevSlide());
		viewMenu.add(prevItem);

		MenuItem aboutItem = new MenuItem("About");
		aboutItem.addActionListener(e -> AboutBox.show(parent));
		viewMenu.add(aboutItem);
		add(viewMenu);
	}

	public void openFile() {
		FileDialog dialog = new FileDialog(parent, "Open File", FileDialog.LOAD);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String filename = dialog.getDirectory() + dialog.getFile();
			try {
				presentation.load(filename);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void saveFile() {
		FileDialog dialog = new FileDialog(parent, "Save File", FileDialog.SAVE);
		dialog.setVisible(true);
		if (dialog.getFile() != null) {
			String filename = dialog.getDirectory() + dialog.getFile();
			try {
				presentation.save(filename);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
