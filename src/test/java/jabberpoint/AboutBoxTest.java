package jabberpoint;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import javax.swing.JOptionPane;
import java.awt.Frame;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
public class AboutBoxTest {

    @Test
    void testShowWithNullParent() {
        // Mocking the static method of JOptionPane /
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            // Call the AboutBox.show() method with null parent
            AboutBox.show(null);

            // Verify the showMessageDialog is called with the expected arguments
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(null), // Parent is null
                    argThat(message -> message instanceof String
                            && ((String) message).contains("JabberPoint is a primitive slide-show program")), // Correct
                                                                                                              // way to
                                                                                                              // check
                                                                                                              // String
                    eq("About JabberPoint\n"), // Title
                    eq(JOptionPane.INFORMATION_MESSAGE) // Message type should be INFORMATION
            ));
        }
    }

    @Test
    void testShowWithFrameParent() {
        // Mocking the static method of JOptionPane
        try (MockedStatic<JOptionPane> mocked = mockStatic(JOptionPane.class)) {
            // Create a dummy Frame as parent
            Frame dummyFrame = new Frame();

            // Call the AboutBox.show() method with the frame parent
            AboutBox.show(dummyFrame);

            // Verify the showMessageDialog is called with the expected arguments
            mocked.verify(() -> JOptionPane.showMessageDialog(
                    eq(dummyFrame), // Frame as parent
                    argThat(message -> message instanceof String
                            && ((String) message).contains("JabberPoint is a primitive slide-show program")), // Correct
                                                                                                              // way to
                                                                                                              // check
                                                                                                              // String
                    eq("About JabberPoint\n"), // Title
                    eq(JOptionPane.INFORMATION_MESSAGE) // Message type should be INFORMATION
            ));
        }
    }
}
