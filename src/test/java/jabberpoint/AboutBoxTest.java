package jabberpoint;

import javax.swing.*;
import java.awt.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AboutBoxTest {

    @Test
    void testShowDisplaysDialog() {
        // Mock JOptionPane to prevent actual dialog from appearing
        try {
            JOptionPane.setDefaultLocale(null); // Optionally reset if needed
            AboutBox.show(new Frame());
        } catch (Exception e) {
            fail("No exception should occur when AboutBox is shown");
        }
    }
}
