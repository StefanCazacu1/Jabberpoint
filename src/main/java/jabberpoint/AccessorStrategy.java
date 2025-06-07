package jabberpoint;

import java.io.IOException;

public interface AccessorStrategy {
    void loadFile(Presentation presentation, String filename) throws IOException;
    void saveFile(Presentation presentation, String filename) throws IOException;
}
