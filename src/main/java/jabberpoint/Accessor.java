package jabberpoint;

import java.io.IOException;

/**
 * Handles loading and saving presentations using a specified strategy.
 */
public class Accessor
{

    /**
     * The strategy used for file access (XML, JSON, etc).
     */
    private AccessorStrategy accessorStrategy;

    /**
     * Sets the accessor strategy.
     *
     * @param strategy the accessor strategy to use
     */
    public void setStrategy(final AccessorStrategy strategy)
    {
        this.accessorStrategy = strategy;
    }

    /**
     * Loads a presentation from a file using the set strategy.
     *
     * @param presentation the presentation to load into
     * @param filename     the filename to load from
     * @throws IOException if loading fails
     */
    public void load(final Presentation presentation, final String filename) throws IOException
    {
        if (accessorStrategy == null)
        {
            throw new IllegalStateException("No AccessorStrategy set!");
        }
        accessorStrategy.loadFile(presentation, filename);
    }

    /**
     * Saves a presentation to a file using the set strategy.
     *
     * @param presentation the presentation to save
     * @param filename     the filename to save to
     * @throws IOException if saving fails
     */
    public void save(final Presentation presentation, final String filename) throws IOException
    {
        if (accessorStrategy == null)
        {
            throw new IllegalStateException("No AccessorStrategy set!");
        }
        accessorStrategy.saveFile(presentation, filename);
    }

}
