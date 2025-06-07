package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

class XMLAccessorTest {

    private Presentation presentation;
    private XMLAccessor xmlAccessor;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        presentation = new Presentation();
        xmlAccessor = new XMLAccessor();
        tempFile = File.createTempFile("test_presentation", ".xml");
        tempFile.deleteOnExit(); // Ensure temp file deleted on JVM exit
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testSaveAndLoadPresentation() throws IOException {
        // Arrange: Create a presentation
        presentation.setTitle("Test Title");
        Slide slide = new Slide();
        slide.append(new TextItem(1, "Sample Text"));
        presentation.addSlide(slide);

        // Act: Save then load
        xmlAccessor.saveFile(presentation, tempFile.getAbsolutePath());
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadFile(loadedPresentation, tempFile.getAbsolutePath());

        // Assert
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
