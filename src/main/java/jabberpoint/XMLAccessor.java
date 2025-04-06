package jabberpoint;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class XMLAccessor extends Accessor {
	protected static final String SHOWTITLE = "showtitle";
	protected static final String SLIDETITLE = "title";
	protected static final String SLIDE = "slide";
	protected static final String ITEM = "item";
	protected static final String LEVEL = "level";
	protected static final String KIND = "kind";
	protected static final String TEXT = "text";
	protected static final String IMAGE = "image";
	protected static final String PCE = "Parser Configuration Exception";
	protected static final String UNKNOWNTYPE = "Unknown Element type";
	protected static final String NFE = "Number Format Exception";

	private String getTitle(Element element, String tagName) {
		NodeList titles = element.getElementsByTagName(tagName);
		return titles.item(0).getTextContent();
	}

	public void loadFile(Presentation presentation, String filename) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename));
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTitle(doc, SHOWTITLE));

			NodeList slides = doc.getElementsByTagName(SLIDE);
			for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
				Element xmlSlide = (Element) slides.item(slideNumber);
				Slide slide = new Slide();
				slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
				presentation.append(slide);

				NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
				for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
					Element item = (Element) slideItems.item(itemNumber);
					loadSlideItem(slide, item);
				}
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
			System.err.println(e.getMessage());
		}
	}

	protected void loadSlideItem(Slide slide, Element item) {
		int level = 1;
		NamedNodeMap attributes = item.getAttributes();
		String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
		if (leveltext != null) {
			try {
				level = Integer.parseInt(leveltext);
			} catch (NumberFormatException e) {
				System.err.println(NFE);
			}
		}
		String type = attributes.getNamedItem(KIND).getTextContent();
		if (TEXT.equals(type)) {
			slide.addItem(new TextItem(level, item.getTextContent()));
		} else if (IMAGE.equals(type)) {
			slide.addItem(new BitmapItem(level, item.getTextContent()));
		} else {
			System.err.println(UNKNOWNTYPE);
		}
	}

	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.println("<showtitle>" + presentation.getTitle() + "</showtitle>");

		for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			out.println("<slide>");
			out.println("<title>" + slide.getTitle() + "</title>");
			for (SlideItem item : slide.getItems()) {
				out.print("<item kind=");
				if (item instanceof TextItem) {
					out.print("\"text\" level=\"" + item.getLevel() + "\">");
					out.print(((TextItem) item).getText());
				} else if (item instanceof BitmapItem) {
					out.print("\"image\" level=\"" + item.getLevel() + "\">");
					out.print(((BitmapItem) item).getName());
				}
				out.println("</item>");
			}
			out.println("</slide>");
		}

		out.println("</presentation>");
		out.close();
	}
}
