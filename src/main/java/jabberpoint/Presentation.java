package jabberpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presentation {
	private String title;
	private List<Slide> slides;
	private int currentSlideNumber;
	private List<Observer> observers;
	private Accessor accessor;

	public Presentation() {
		slides = new ArrayList<>();
		observers = new ArrayList<>();
		accessor = new Accessor();
		clear();
	}

	public void clear() {
		slides.clear();
		setTitle("");
		setSlideNumber(-1);
		notifyObservers();
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	private void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}

	public int getSize() {
		return slides.size();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		notifyObservers();
	}

	public void addSlide(Slide slide) {
		slides.add(slide);
		notifyObservers();
	}

	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return slides.get(number);
	}

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	public void setSlideNumber(int number) {
		this.currentSlideNumber = number;
		notifyObservers();
	}

	public Slide getCurrentSlide() {
		if (currentSlideNumber < 0 || currentSlideNumber >= slides.size()) {
			return null;
		}
		return slides.get(currentSlideNumber);
	}

	public void nextSlide() {
		if (currentSlideNumber < slides.size() - 1) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	public void load(String filename) throws IOException {
		if (filename.endsWith(".xml")) {
			accessor.setStrategy(new XMLAccessor());
		} else if (filename.endsWith(".json")) {
			accessor.setStrategy(new JsonAccessor());
		} else {
			throw new IOException("Unsupported file type");
		}
		accessor.load(this, filename);
		setSlideNumber(0);
		notifyObservers();
	}

	public void save(String filename) throws IOException {
		if (filename.endsWith(".xml")) {
			accessor.setStrategy(new XMLAccessor());
		} else if (filename.endsWith(".json")) {
			accessor.setStrategy(new JsonAccessor());
		} else {
			throw new IOException("Unsupported file type");
		}
		accessor.save(this, filename);
	}

	// ADD this to Presentation.java
	public List<Slide> getSlides() {
		return slides;
	}

}
