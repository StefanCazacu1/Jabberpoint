package jabberpoint;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import javax.swing.JOptionPane;
import java.awt.Frame;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

/**
 * Unit tests for the AboutBox class.
 */
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class AboutBoxTest {

    /**
     * Tests AboutBox.show() with a null parent.
     */
    @Test
    void testShowWithNullParent() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            AboutBox.show(null);
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(null),
                    argThat(message -> message instanceof String
                            && ((String) message).contains("JabberPoint is a primitive slide-show")),
                    eq("About JabberPoint"), // Removed \n here
                    eq(JOptionPane.INFORMATION_MESSAGE)
            ));
        }
    }

    /**
     * Tests AboutBox.show() with a Frame parent.
     */
    @Test
    void testShowWithFrameParent() {
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            Frame dummyFrame = new Frame();
            AboutBox.show(dummyFrame);
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(dummyFrame),
                    argThat(message -> message instanceof String
                            && ((String) message).contains("JabberPoint is a primitive slide-show")),
                    eq("About JabberPoint"), // Removed \n here
                    eq(JOptionPane.INFORMATION_MESSAGE)
            ));
        }
    }
}
