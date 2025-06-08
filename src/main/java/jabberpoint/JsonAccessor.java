package jabberpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

/**
 * Accessor for loading and saving Presentation data in JSON format.
 * This class is final and not designed for extension.
 */
public final class JsonAccessor implements AccessorStrategy {

    /**
     * Loads a Presentation from a JSON file.
     *
     * @param presentation the Presentation to populate
     * @param filename the JSON filename
     * @throws IOException if an error occurs while reading
     */
    @Override
    public void loadFile(final Presentation presentation, final String filename)
            throws IOException {
        presentation.clear();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filename));

        // Load the title
        JsonNode titleNode = root.get("title");
        if (titleNode != null) {
            presentation.setTitle(titleNode.asText());
        }

        // Load the slides
        JsonNode slidesNode = root.get("slides");
        if (slidesNode != null && slidesNode.isArray()) {
            for (JsonNode slideNode : slidesNode) {
                Slide slide = new Slide();
                JsonNode itemsNode = slideNode.get("items");
                if (itemsNode != null && itemsNode.isArray()) {
                    for (JsonNode itemNode : itemsNode) {
                        String kind = itemNode.get("kind").asText();
                        int level = itemNode.get("level").asInt();
                        String content = itemNode.get("content").asText();

                        if ("text".equals(kind)) {
                            slide.addItem(new TextItem(level, content));
                        } else if ("image".equals(kind)) {
                            slide.addItem(new BitmapItem(level, content));
                        }
                    }
                }
                presentation.addSlide(slide);
            }
        }
    }

    /**
     * Saves a Presentation to a JSON file.
     *
     * @param presentation the Presentation to save
     * @param filename the JSON filename
     * @throws IOException if an error occurs while writing
     */
    @Override
    public void saveFile(final Presentation presentation, final String filename)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        // Save title
        root.put("title", presentation.getTitle() != null
                ? presentation.getTitle() : "");

        // Save slides
        ArrayNode slidesArray = mapper.createArrayNode();
        for (Slide slide : presentation.getSlides()) {
            ObjectNode slideNode = mapper.createObjectNode();
            ArrayNode itemsArray = mapper.createArrayNode();

            for (SlideItem item : slide.getSlideItems()) {
                ObjectNode itemNode = mapper.createObjectNode();
                if (item instanceof TextItem) {
                    itemNode.put("kind", "text");
                    itemNode.put("content", ((TextItem) item).getText());
                } else if (item instanceof BitmapItem) {
                    itemNode.put("kind", "image");
                    itemNode.put("content", ((BitmapItem) item).getName());
                } else {
                    itemNode.put("kind", "unknown");
                    itemNode.put("content", item.toString());
                }
                itemNode.put("level", item.getLevel());
                itemsArray.add(itemNode);
            }

            slideNode.set("items", itemsArray);
            slidesArray.add(slideNode);
        }

        root.set("slides", slidesArray);

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(filename), root);
    }
}
