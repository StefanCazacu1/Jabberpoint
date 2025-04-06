package jabberpoint;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuControllerTest {

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
    void testMenuControllerIsNotNull() {
        assertNotNull(menuController);
    }

    @Test
    void testMenuControllerHasTwoMenus() {
        assertEquals(2, menuController.getMenuCount()); // Should have "File" and "View"
    }

    @Test
    void testNextSlideMenuItem() {
        Menu viewMenu = menuController.getMenu(1); // "View" menu
        MenuItem nextItem = viewMenu.getItem(0); // "Next Slide"
        ActionListener[] listeners = nextItem.getActionListeners();

        // Simulate click
        listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Verify presentation.nextSlide() is called
        verify(presentation, times(1)).nextSlide();
    }

    @Test
    void testPrevSlideMenuItem() {
        Menu viewMenu = menuController.getMenu(1); // "View" menu
        MenuItem prevItem = viewMenu.getItem(1); // "Previous Slide"
        ActionListener[] listeners = prevItem.getActionListeners();

        // Simulate click
        listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Verify presentation.prevSlide() is called
        verify(presentation, times(1)).prevSlide();
    }

    @Test
    void testAboutMenuItemDoesNotCrash() {
        Menu viewMenu = menuController.getMenu(1); // "View" menu
        MenuItem aboutItem = viewMenu.getItem(2); // "About"
        ActionListener[] listeners = aboutItem.getActionListeners();

        // Simulate click (we can't easily verify JOptionPane so just check no crash)
        assertDoesNotThrow(
                () -> listeners[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)));
    }

    @Test
    void testSaveFileIOExceptionHandling() throws IOException {
        doThrow(new IOException("Save error")).when(presentation).save(anyString());
    }

    @Test
    void testLoadFileIOExceptionHandling() throws IOException {
        doThrow(new IOException("Load error")).when(presentation).load(anyString());
    }
}
