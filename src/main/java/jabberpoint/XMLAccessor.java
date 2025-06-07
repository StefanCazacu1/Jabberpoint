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

    /**
     * Loads a Presentation from an XML file.
     *
     * @param presentation the Presentation to populate
     * @param filename     the XML filename
     * @throws IOException if any IO or parse error occurs
     */
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

            presentation.setTitle(root.getAttribute("title"));

            NodeList slides = root.getElementsByTagName("slide");
            for (int i = 0; i < slides.getLength(); i++) {
                Element slideElement = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(slideElement.getAttribute("title"));

                NodeList items = slideElement.getElementsByTagName("item");
                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);
                    int level = Integer.parseInt(item.getAttribute("level"));
                    String kind = item.getAttribute("kind");
                    String content = item.getTextContent();

                    if ("text".equals(kind)) {
                        slide.append(new TextItem(level, content));
                    } else if ("image".equals(kind)) {
                        slide.append(new BitmapItem(level, content));
                    }
                }
                presentation.addSlide(slide);
            }
        } catch (Exception e) {
            throw new IOException("Failed to load XML: " + e.getMessage(), e);
        }
    }

    /**
     * Saves a Presentation to an XML file.
     *
     * @param presentation the Presentation to save
     * @param filename     the XML filename
     * @throws IOException if any IO or write error occurs
     */
    @Override
    public void saveFile(final Presentation presentation,
            final String filename) throws IOException {
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("presentation");
            root.setAttribute("title", presentation.getTitle());
            document.appendChild(root);

            for (Slide slide : presentation.getSlides()) {
                Element slideElement = document.createElement("slide");
                slideElement.setAttribute("title", slide.getTitle());
                root.appendChild(slideElement);

                for (SlideItem item : slide.getSlideItems()) {
                    Element itemElement = document.createElement("item");
                    itemElement.setAttribute("level",
                            String.valueOf(item.getLevel()));

                    if (item instanceof TextItem) {
                        itemElement.setAttribute("kind", "text");
                        itemElement.setTextContent(
                                ((TextItem) item).getText());
                    } else if (item instanceof BitmapItem) {
                        itemElement.setAttribute("kind", "image");
                        itemElement.setTextContent(((BitmapItem) item).getName());
                    }
                    slideElement.appendChild(itemElement);
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2"
            );

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
