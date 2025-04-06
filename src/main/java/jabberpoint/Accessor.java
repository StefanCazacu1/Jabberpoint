package jabberpoint;

import java.io.IOException;

public class Accessor {
	private AccessorStrategy strategy;

	public void setStrategy(AccessorStrategy strategy) {
		this.strategy = strategy;
	}

	public void load(Presentation presentation, String filename) throws IOException {
		if (strategy == null)
			throw new IllegalStateException("No AccessorStrategy set!");
		strategy.loadFile(presentation, filename);
	}

	public void save(Presentation presentation, String filename) throws IOException {
		if (strategy == null)
			throw new IllegalStateException("No AccessorStrategy set!");
		strategy.saveFile(presentation, filename);
	}
}
