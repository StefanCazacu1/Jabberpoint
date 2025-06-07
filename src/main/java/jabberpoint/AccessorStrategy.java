package jabberpoint;

import java.io.IOException;

/**
 * Strategy interface for loading and saving presentations.
 */
public interface AccessorStrategy {

    /**
     * Loads a presentation from the given file.
     *
     * @param presentation the presentation to populate
     * @param filename     the filename to load from
     * @throws IOException if loading fails
     */
    void loadFile(Presentation presentation, String filename) throws IOException;

    /**
     * Saves a presentation to the given file.
     *
     * @param presentation the presentation to save
     * @param filename     the filename to save to
     * @throws IOException if saving fails
     */
    void saveFile(Presentation presentation, String filename) throws IOException;
}
