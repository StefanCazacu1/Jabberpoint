package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class XMLAccessorTest {

    private Presentation presentation;
    private XMLAccessor xmlAccessor;
    private final String tempFile = System.getProperty("java.io.tmpdir") + File.separator + "test_presentation.xml";

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        xmlAccessor = new XMLAccessor();
    }

    @Test
    void testSaveAndLoadPresentation() throws IOException {
        // Arrange: Create a presentation
        presentation.setTitle("Test Title");
        Slide slide = new Slide();
        slide.append(new TextItem(1, "Sample Text"));
        presentation.addSlide(slide);

        // Act: Save it
        xmlAccessor.saveFile(presentation, tempFile);

        // Load it back
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadFile(loadedPresentation, tempFile);

        // Assert: Check title and slide
        assertEquals("Test Title", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());
        Slide loadedSlide = loadedPresentation.getSlide(0);
        assertNotNull(loadedSlide);
        assertEquals(1, loadedSlide.getSlideItems().size());
        assertTrue(loadedSlide.getSlideItems().get(0) instanceof TextItem);
        assertEquals("Sample Text", loadedSlide.getSlideItems().get(0).toString());
    }

    @Test
    void testSaveToInvalidPathThrowsException() {
        Presentation faultyPresentation = new Presentation();
        assertThrows(IOException.class, () -> {
            xmlAccessor.saveFile(faultyPresentation, "/invalid/path/illegal.xml");
        });
    }

    @Test
    void testLoadNonExistentFileThrowsException() {
        assertThrows(IOException.class, () -> {
            xmlAccessor.loadFile(new Presentation(), "nonexistent_file.xml");
        });
    }
}
