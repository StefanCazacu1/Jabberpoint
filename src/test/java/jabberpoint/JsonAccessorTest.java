package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonAccessorTest {

    private Presentation presentation;
    private JsonAccessor accessor;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        accessor = new JsonAccessor();
    }

    @Test
    public void testSaveAndLoadPresentation() throws IOException {
        // Create a presentation
        presentation.setTitle("Test Presentation");
        Slide slide = new Slide();
        slide.append(new TextItem(0, "Welcome to JabberPoint"));
        presentation.addSlide(slide);

        File tempFile = File.createTempFile("test_presentation", ".json");

        // Save the file
        accessor.saveFile(presentation, tempFile.getAbsolutePath());

        // Load the file into a new presentation
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, tempFile.getAbsolutePath());

        assertEquals("Test Presentation", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());

        Slide loadedSlide = loadedPresentation.getSlide(0);
        assertNotNull(loadedSlide);

        List<SlideItem> items = loadedSlide.getSlideItems();
        assertEquals(1, items.size());

        SlideItem item = items.get(0);
        assertTrue(item instanceof TextItem);
        assertEquals(0, item.getLevel());
        assertEquals("Welcome to JabberPoint", item.toString());

        Files.deleteIfExists(tempFile.toPath());
    }

    @Test
    public void testSaveAndLoadEmptyPresentation() throws IOException {
        presentation.setTitle(null);
        File tempFile = File.createTempFile("empty_presentation", ".json");

        accessor.saveFile(presentation, tempFile.getAbsolutePath());

        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, tempFile.getAbsolutePath());

        assertEquals("", loadedPresentation.getTitle());
        assertEquals(0, loadedPresentation.getSize());

        Files.deleteIfExists(tempFile.toPath());
    }

    @Test
    public void testLoadInvalidFileThrowsIOException() {
        Presentation newPresentation = new Presentation();
        assertThrows(IOException.class, () -> accessor.loadFile(newPresentation, "non_existing_file.json"));
    }

    @Test
    public void testSavePresentationToInvalidPathThrowsIOException() {
        Presentation newPresentation = new Presentation();
        assertThrows(IOException.class, () -> accessor.saveFile(newPresentation, "?:/invalid_path/test.json"));
    }
}
