package jabberpoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provides methods to load and save Presentations to and from XML files.
 */
public final class XMLAccessor implements AccessorStrategy {

    @Override
    public void loadFile(final Presentation presentation,
            final String filename) throws IOException {
        try {
            presentation.clear();
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element root = document.getDocumentElement();

            String titleAttr = root.getAttribute("title");
            presentation.setTitle(titleAttr != null ? titleAttr : "");

            NodeList slides = root.getElementsByTagName("slide");
            for (int i = 0; i < slides.getLength(); i++) {
                Element slideElement = (Element) slides.item(i);
                Slide slide = new Slide();

                String slideTitle = slideElement.getAttribute("title");
                slide.setTitle(slideTitle != null ? slideTitle : "");

                NodeList items = slideElement.getElementsByTagName("item");
                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);
                    int level = Integer.parseInt(item.getAttribute("level"));
                    String kind = item.getAttribute("kind");
                    String content = item.getTextContent();

                    if ("text".equals(kind)) {
                        slide.addItem(new TextItem(level,
                                content != null ? content : ""));
                    } else if ("image".equals(kind)) {
                        slide.addItem(new BitmapItem(level,
                                content != null ? content : ""));
                    }
                }
                presentation.addSlide(slide);
            }
        } catch (Exception e) {
            throw new IOException("Failed to load XML: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveFile(final Presentation presentation,
            final String filename) throws IOException {
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("presentation");
            root.setAttribute("title", presentation.getTitle() !=
                    null ? presentation.getTitle() : "");
            document.appendChild(root);

            for (Slide slide : presentation.getSlides()) {
                Element slideElement = document.createElement("slide");
                slideElement.setAttribute("title", slide.getTitle() !=
                        null ? slide.getTitle() : "");
                root.appendChild(slideElement);

                for (SlideItem item : slide.getSlideItems()) {
                    Element itemElement = document.createElement("item");
                    itemElement.setAttribute("level",
                            String.valueOf(item.getLevel()));

                    if (item instanceof TextItem) {
                        itemElement.setAttribute("kind", "text");
                        String textContent = ((TextItem) item).getText();
                        itemElement.setTextContent(textContent !=
                                null ? textContent : "");
                    } else if (item instanceof BitmapItem) {
                        itemElement.setAttribute("kind", "image");
                        String name = ((BitmapItem) item).getName();
                        itemElement.setTextContent(name != null ? name : "");
                    }
                    slideElement.appendChild(itemElement);
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filename);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
            fos.close();
        } catch (Exception e) {
            throw new IOException("Failed to save XML: " + e.getMessage(), e);
        }
    }
}
