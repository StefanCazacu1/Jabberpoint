package jabberpoint;

/**
 * Observer interface for JabberPoint.
 * Implement this interface to receive update notifications.
 */
public interface Observer
{

    /**
     * Called when the observed object is updated.
     */
    void update();

}
