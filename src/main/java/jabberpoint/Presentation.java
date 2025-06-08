package jabberpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Presentation class manages slides and observers for JabberPoint.
 */
public class Presentation {
    /** The title of the presentation. */
    private String title;

    /** List of slides in the presentation. */
    private final List<Slide> slides;

    /** The index of the current slide. */
    private int currentSlideNumber;

    /** List of observers monitoring changes. */
    private final List<Observer> observers;

    /** Accessor to load/save presentations in various formats. */
    private final Accessor accessor;

    /** Flag to indicate if state changed to control notifications. */
    private boolean changed;

    /**
     * Constructs a new Presentation.
     */
    public Presentation() {
        slides = new ArrayList<>();
        observers = new ArrayList<>();
        accessor = new Accessor();
        clear();
    }

    /**
     * Clears the presentation, removing all slides and resetting the title.
     */
    public void clear() {
        slides.clear();
        setTitle("");
        setSlideNumber(-1);
        setChanged();
        notifyObservers();
    }

    /**
     * Adds an observer to the presentation.
     * @param observer the observer to add
     */
    public synchronized void addObserver(final Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the presentation.
     * @param observer the observer to remove
     */
    public synchronized void removeObserver(final Observer observer) {
        observers.remove(observer);
    }

    /**
     * Marks the state as changed.
     */
    private synchronized void setChanged() {
        changed = true;
    }

    /**
     * Clears the changed flag.
     */
    private synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Checks if the state has changed.
     * @return true if changed, false otherwise
     */
    private synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * Notifies all observers if state has changed.
     */
    private void notifyObservers() {
        List<Observer> observersLocal;
        synchronized (this) {
            if (!hasChanged()) {
                return;
            }
            observersLocal = new ArrayList<>(observers);
            clearChanged();
        }
        for (Observer observer : observersLocal) {
            observer.update();
        }
    }

    /**
     * Public method to notify observers that presentation content changed.
     * Call this after internal modifications that do not change slide number or title.
     */
    public void notifyPresentationChanged() {
        setChanged();
        notifyObservers();
    }

    /**
     * Gets the number of slides.
     * @return the number of slides
     */
    public int getSize() {
        return slides.size();
    }

    /**
     * Gets the presentation title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the presentation title.
     * @param newTitle the new title
     */
    public void setTitle(final String newTitle) {
        String titleToSet = newTitle;
        if (titleToSet == null) {
            titleToSet = "";
        }
        if (!titleToSet.equals(this.title)) {
            this.title = titleToSet;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Adds a slide to the presentation.
     * @param slide the slide to add
     */
    public void addSlide(final Slide slide) {
        slides.add(slide);
        setChanged();
        notifyObservers();
    }

    /**
     * Gets a slide by its index.
     * @param number the slide index
     * @return the slide, or null if index is invalid
     */
    public Slide getSlide(final int number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }
        return slides.get(number);
    }

    /**
     * Gets the current slide number.
     * @return the current slide index
     */
    public int getSlideNumber() {
        return currentSlideNumber;
    }

    /**
     * Sets the current slide number.
     * @param number the new slide index
     */
    public void setSlideNumber(final int number) {
        if (this.currentSlideNumber != number) {
            this.currentSlideNumber = number;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Gets the current slide.
     * @return the current slide, or null if invalid index
     */
    public Slide getCurrentSlide() {
        if (currentSlideNumber < 0 || currentSlideNumber >= slides.size()) {
            return null;
        }
        return slides.get(currentSlideNumber);
    }

    /**
     * Advances to the next slide.
     */
    public void nextSlide() {
        if (currentSlideNumber < slides.size() - 1) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    /**
     * Goes back to the previous slide.
     */
    public void prevSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    /**
     * Loads a presentation from a file (XML or JSON).
     * @param filename the file to load from
     * @throws IOException if an error occurs
     */
    public void load(final String filename) throws IOException {
        if (filename.endsWith(".xml")) {
            accessor.setStrategy(new XMLAccessor());
        } else if (filename.endsWith(".json")) {
            accessor.setStrategy(new JsonAccessor());
        } else {
            throw new IOException("Unsupported file type");
        }
        accessor.load(this, filename);
        setSlideNumber(0);
        setChanged();
        notifyObservers();
    }

    /**
     * Saves a presentation to a file (XML or JSON).
     * @param filename the file to save to
     * @throws IOException if an error occurs
     */
    public void save(final String filename) throws IOException {
        if (filename.endsWith(".xml")) {
            accessor.setStrategy(new XMLAccessor());
        } else if (filename.endsWith(".json")) {
            accessor.setStrategy(new JsonAccessor());
        } else {
            throw new IOException("Unsupported file type");
        }
        accessor.save(this, filename);
    }

    /**
     * Gets all slides as a list.
     * @return the slides
     */
    public List<Slide> getSlides() {
        return slides;
    }
}
