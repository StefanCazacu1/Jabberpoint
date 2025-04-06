package jabberpoint;

class DemoPresentation extends Accessor {

	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo Presentation");
		Slide slide;

		slide = new Slide();
		slide.setTitle("JabberPoint");
		slide.addItem(new TextItem(1, "Het Java Presentatie Tool"));
		slide.addItem(new TextItem(2, "Copyright (c) 1996-2000: Ian Darwin"));
		slide.addItem(new TextItem(2, "Copyright (c) 2000-now:"));
		slide.addItem(new TextItem(2, "Gert Florijn en Sylvia Stuurman"));
		slide.addItem(new TextItem(4, "JabberPoint aanroepen zonder bestandsnaam"));
		slide.addItem(new TextItem(4, "laat deze presentatie zien"));
		slide.addItem(new TextItem(1, "Navigeren:"));
		slide.addItem(new TextItem(3, "Volgende slide: PgDn of Enter"));
		slide.addItem(new TextItem(3, "Vorige slide: PgUp of up-arrow"));
		slide.addItem(new TextItem(3, "Stoppen: q or Q"));
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("Demonstratie van levels en stijlen");
		slide.addItem(new TextItem(1, "Level 1"));
		slide.addItem(new TextItem(2, "Level 2"));
		slide.addItem(new TextItem(1, "Nogmaals level 1"));
		slide.addItem(new TextItem(1, "Level 1 heeft stijl nummer 1"));
		slide.addItem(new TextItem(2, "Level 2 heeft stijl nummer 2"));
		slide.addItem(new TextItem(3, "Zo ziet level 3 er uit"));
		slide.addItem(new TextItem(4, "En dit is level 4"));
		presentation.append(slide);

		slide = new Slide();
		slide.setTitle("De derde slide");
		slide.addItem(new TextItem(1, "Om een nieuwe presentatie te openen,"));
		slide.addItem(new TextItem(2, "gebruik File->Open uit het menu."));
		slide.addItem(new TextItem(1, " "));
		slide.addItem(new TextItem(1, "Dit is het einde van de presentatie."));
		slide.addItem(new BitmapItem(1, "JabberPoint.jpg"));
		presentation.append(slide);
	}

	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException("Save As->Demo! aangeroepen");
	}
}
