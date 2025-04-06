package jabberpoint;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLAccessor implements AccessorStrategy {

	@Override
	public void loadFile(Presentation presentation, String filename) throws IOException {
		presentation.clear();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename));
			
			// Load the title
			NodeList titleNodes = document.getElementsByTagName("title");
			if (titleNodes.getLength() > 0) {
				String title = titleNodes.item(0).getTextContent();
				presentation.setTitle(title);
			}

			// Load slides
			NodeList slideNodes = document.getElementsByTagName("slide");
			for (int i = 0; i < slideNodes.getLength(); i++) {
				Element slideElement = (Element) slideNodes.item(i);
				Slide slide = new Slide();

				NodeList itemNodes = slideElement.getElementsByTagName("item");
				for (int j = 0; j < itemNodes.getLength(); j++) {
					Element itemElement = (Element) itemNodes.item(j);
					String kind = itemElement.getAttribute("kind");
					int level = Integer.parseInt(itemElement.getAttribute("level"));
					String content = itemElement.getTextContent();

					if ("text".equals(kind)) {
						slide.append(new TextItem(level, content));
					}
				}
				presentation.addSlide(slide);
			}
		} catch (Exception e) {
			throw new IOException("Error loading presentation: " + e.getMessage(), e);
		}
	}

	@Override
	public void saveFile(Presentation presentation, String filename) throws IOException {
		File file = new File(filename);
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			throw new IOException("Directory does not exist: " + file.getParent());
		}

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("presentation");
			document.appendChild(root);

			// Save title
			Element titleElement = document.createElement("title");
			titleElement.setTextContent(presentation.getTitle() != null ? presentation.getTitle() : "");
			root.appendChild(titleElement);

			// Save slides
			for (Slide slide : presentation.getSlides()) {
				Element slideElement = document.createElement("slide");
				root.appendChild(slideElement);

				for (SlideItem item : slide.getSlideItems()) {
					Element itemElement = document.createElement("item");
					itemElement.setAttribute("kind", item instanceof TextItem ? "text" : "unknown");
					itemElement.setAttribute("level", Integer.toString(item.getLevel()));
					itemElement.setTextContent(item.toString());
					slideElement.appendChild(itemElement);
				}
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(file));

		} catch (ParserConfigurationException | TransformerException e) {
			throw new IOException("Error saving presentation: " + e.getMessage(), e);
		}
	}
}
