package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class MenuControllerTest {

    private static final int FILE_MENU_INDEX = 0;
    private static final int EDIT_MENU_INDEX = 1;
    private static final int NAVIGATE_MENU_INDEX = 2;
    private static final int VIEW_MENU_INDEX = 3;

    private Presentation presentation;
    private Frame frame;
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        presentation = mock(Presentation.class);
        frame = new Frame();
        menuController = new MenuController(frame, presentation);
    }

    @Test
    void testMenuCount() {
        assertEquals(4, menuController.getMenuCount(), "Should have 4 top-level menus");
    }

    @Test
    void testFileMenuItems() {
        Menu fileMenu = menuController.getMenu(FILE_MENU_INDEX);
        assertEquals("File", fileMenu.getLabel());
        // Expecting 4 items: New Presentation, Open, Save, Exit
        assertEquals(4, fileMenu.getItemCount());
    }

    @Test
    void testEditMenuItems() {
        Menu editMenu = menuController.getMenu(EDIT_MENU_INDEX);
        assertEquals("Edit", editMenu.getLabel());
        // Expecting 5 items: Add Slide, Remove Slide, Add Text Item, Add Image Item, Remove Slide Item
        assertEquals(5, editMenu.getItemCount());
    }

    @Test
    void testNavigateMenuItems() {
        Menu navigateMenu = menuController.getMenu(NAVIGATE_MENU_INDEX);
        assertEquals("Navigate", navigateMenu.getLabel());
        // Expecting 2 items: Next, Prev
        assertEquals(2, navigateMenu.getItemCount());
    }

    @Test
    void testViewMenuHasAbout() {
        Menu viewMenu = menuController.getMenu(VIEW_MENU_INDEX);
        assertEquals("View", viewMenu.getLabel());
        assertEquals(1, viewMenu.getItemCount());
        MenuItem aboutItem = viewMenu.getItem(0);
        assertEquals("About", aboutItem.getLabel());

        ActionListener[] listeners = aboutItem.getActionListeners();
        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

    @Test
    void testNextMenuCallsNextSlide() {
        Menu navigateMenu = menuController.getMenu(NAVIGATE_MENU_INDEX); // index 2
        assertEquals("Navigate", navigateMenu.getLabel());

        MenuItem nextItem = navigateMenu.getItem(0);
        assertNotNull(nextItem);
        assertEquals("Next", nextItem.getLabel());  // Check item label

        // Trigger the menu item action
        for (ActionListener listener : nextItem.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(presentation, times(1)).nextSlide();
    }

    @Test
    void testPrevMenuCallsPrevSlide() {
        Menu navigateMenu = menuController.getMenu(NAVIGATE_MENU_INDEX); // index 2
        MenuItem prevItem = navigateMenu.getItem(1);
        assertEquals("Prev", prevItem.getLabel());

        for (ActionListener listener : prevItem.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
        verify(presentation, times(1)).prevSlide();
    }

    @Test
    void testSaveFileHandlesIOException() throws IOException {
        doThrow(new IOException("Save error")).when(presentation).save(anyString());

        Menu fileMenu = menuController.getMenu(FILE_MENU_INDEX);
        MenuItem saveItem = fileMenu.getItem(2); // Save is third item
        ActionListener[] listeners = saveItem.getActionListeners();

        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

    @Test
    void testOpenFileHandlesIOException() throws IOException {
        doThrow(new IOException("Load error")).when(presentation).load(anyString());

        Menu fileMenu = menuController.getMenu(FILE_MENU_INDEX);
        MenuItem openItem = fileMenu.getItem(1); // Open is second item
        ActionListener[] listeners = openItem.getActionListeners();

        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }
}
