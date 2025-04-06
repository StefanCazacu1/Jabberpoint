package jabberpoint;

import static org.mockito.Mockito.*;

import javax.swing.JOptionPane;
import java.awt.Frame;

import org.junit.jupiter.api.Test;
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
}
