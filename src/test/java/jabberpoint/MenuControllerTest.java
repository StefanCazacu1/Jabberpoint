package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class MenuControllerTest {

    private static final int FILE_MENU_INDEX = 0;
    private static final int VIEW_MENU_INDEX = 1;

    private Presentation presentation;
    private JFrame frame;
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        presentation = mock(Presentation.class);
        frame = new JFrame();
        menuController = new MenuController(frame, presentation);
    }

    @Test
    @DisplayName("MenuController instance is created successfully")
    void testMenuControllerIsNotNull() {
        assertNotNull(menuController);
    }

    @Test
    @DisplayName("MenuController has exactly two menus: File and View")
    void testMenuControllerHasTwoMenus() {
        assertEquals(2, menuController.getMenuCount());
    }

    @Test
    @DisplayName("Next Slide menu item triggers presentation.nextSlide()")
    void testNextSlideMenuItem() {
        // Get the 'View' menu by index
        Menu viewMenu = menuController.getMenu(VIEW_MENU_INDEX);
        // 'Next Slide' menu item is first in View menu
        MenuItem nextItem = viewMenu.getItem(0);
        ActionListener[] listeners = nextItem.getActionListeners();

        // Simulate clicking the menu item
        listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(presentation, times(1)).nextSlide();
    }

    @Test
    @DisplayName("Previous Slide menu item triggers presentation.prevSlide()")
    void testPrevSlideMenuItem() {
        Menu viewMenu = menuController.getMenu(VIEW_MENU_INDEX);
        MenuItem prevItem = viewMenu.getItem(1);
        ActionListener[] listeners = prevItem.getActionListeners();

        listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(presentation, times(1)).prevSlide();
    }

    @Test
    @DisplayName("About menu item does not throw any exception on click")
    void testAboutMenuItemDoesNotCrash() {
        Menu viewMenu = menuController.getMenu(VIEW_MENU_INDEX);
        MenuItem aboutItem = viewMenu.getItem(2);
        ActionListener[] listeners = aboutItem.getActionListeners();

        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

    @Test
    @DisplayName("Save menu item handles IOException gracefully")
    void testSaveFileIOExceptionHandling() throws IOException {
        doThrow(new IOException("Save error")).when(presentation).save(anyString());

        Menu fileMenu = menuController.getMenu(FILE_MENU_INDEX);
        MenuItem saveItem = fileMenu.getItem(1);
        ActionListener[] listeners = saveItem.getActionListeners();

        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

    @Test
    @DisplayName("Open menu item handles IOException gracefully")
    void testLoadFileIOExceptionHandling() throws IOException {
        doThrow(new IOException("Load error")).when(presentation).load(anyString());

        Menu fileMenu = menuController.getMenu(FILE_MENU_INDEX);
        MenuItem openItem = fileMenu.getItem(0);
        ActionListener[] listeners = openItem.getActionListeners();

        assertDoesNotThrow(() -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

}
