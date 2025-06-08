package jabberpoint;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the JsonAccessor class, including TextItem and BitmapItem.
 */
public class JsonAccessorTest {

    private Presentation presentation;
    private JsonAccessor accessor;

    /**
     * Setup a new presentation and accessor before each test.
     */
    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        accessor = new JsonAccessor();
    }

    /**
     * Test saving and loading a presentation with TextItem and BitmapItem.
     * @throws IOException if file operations fail
     */
    @Test
    public void testSaveAndLoadPresentationWithTextAndImage() throws IOException {
        presentation.setTitle("Test Presentation with Image");
        Slide slide = new Slide();
        slide.addItem(new TextItem(0, "Welcome to JabberPoint"));
        slide.addItem(new BitmapItem(1, "example.png"));
        presentation.addSlide(slide);

        File tempFile = File.createTempFile("test_presentation_img", ".json");
        accessor.saveFile(presentation, tempFile.getAbsolutePath());

        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, tempFile.getAbsolutePath());

        assertEquals("Test Presentation with Image", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());

        Slide loadedSlide = loadedPresentation.getSlide(0);
        assertNotNull(loadedSlide);

        List<SlideItem> items = loadedSlide.getSlideItems();
        assertEquals(2, items.size());

        SlideItem item1 = items.get(0);
        assertTrue(item1 instanceof TextItem);
        assertEquals(0, item1.getLevel());
        assertEquals("Welcome to JabberPoint", item1.toString());

        SlideItem item2 = items.get(1);
        assertTrue(item2 instanceof BitmapItem);
        assertEquals(1, item2.getLevel());
        assertEquals("example.png", item2.toString());

        Files.deleteIfExists(tempFile.toPath());
    }

    // Existing tests below...

    @Test
    public void testSaveAndLoadPresentation() throws IOException {
        presentation.setTitle("Test Presentation");
        Slide slide = new Slide();
        slide.addItem(new TextItem(0, "Welcome to JabberPoint"));
        presentation.addSlide(slide);

        File tempFile = File.createTempFile("test_presentation", ".json");
        accessor.saveFile(presentation, tempFile.getAbsolutePath());

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
