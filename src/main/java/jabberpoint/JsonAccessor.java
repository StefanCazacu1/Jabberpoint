package jabberpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonAccessor implements AccessorStrategy {

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
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
                            slide.append(new TextItem(level, content));
                        }
                        // You could also add image loading here in future
                    }
                }
                presentation.addSlide(slide);
            }
        }
    }

    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        // Save title
        root.put("title", presentation.getTitle() != null ? presentation.getTitle() : "");

        // Save slides
        ArrayNode slidesArray = mapper.createArrayNode();
        for (Slide slide : presentation.getSlides()) {
            ObjectNode slideNode = mapper.createObjectNode();
            ArrayNode itemsArray = mapper.createArrayNode();

            for (SlideItem item : slide.getSlideItems()) {
                ObjectNode itemNode = mapper.createObjectNode();
                itemNode.put("kind", item instanceof TextItem ? "text" : "unknown");
                itemNode.put("level", item.getLevel());
                itemNode.put("content", item.toString()); // or item.getText() if you have such method
                itemsArray.add(itemNode);
            }

            slideNode.set("items", itemsArray);
            slidesArray.add(slideNode);
        }

        root.set("slides", slidesArray);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), root);
    }
}
