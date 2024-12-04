package org.giannico.russo.persistence.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.giannico.russo.persistence.model.enums.Category;

import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        // Controlla se il token è una stringa
        if (parser.getCurrentToken().isScalarValue()) {
            return Category.fromDescription(parser.getText());
        }
        // Se il token è un oggetto, leggi il campo "description"
        else if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
            JsonNode node = parser.getCodec().readTree(parser);
            String description = node.get("description").asText();  // Assicurati che "description" sia il nome del campo nel tuo JSON
            return Category.fromDescription(description);
        }
        // Se non è né una stringa né un oggetto, l'input è invalido
        else {
            throw new IllegalArgumentException("Input mismatch for Category enum");
        }
    }
}
