package jabberpoint;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mockStatic;


import org.junit.jupiter.api.Test;

import javax.swing.JOptionPane;
import java.awt.Frame;

import org.mockito.MockedStatic;

class AboutBoxTest {

    @Test
    void testShowDisplaysDialog() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            Frame dummyFrame = new Frame();
            AboutBox.show(dummyFrame);

            // Verify that JOptionPane.showMessageDialog was called once
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(dummyFrame),
                    contains("JabberPoint is a primitive slide-show program"),
                    eq("About JabberPoint"),
                    eq(JOptionPane.INFORMATION_MESSAGE)));
        }
    }

    @Test
    void testShowWithNullParent() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            // Passing null as the parent
            AboutBox.show(null);

            // Verify that JOptionPane.showMessageDialog was still called
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(null), // null parent
                    contains("JabberPoint is a primitive slide-show program"),
                    eq("About JabberPoint"),
                    eq(JOptionPane.INFORMATION_MESSAGE)));
        }
    }
}
