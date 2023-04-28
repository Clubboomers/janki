package main.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.data.Deck;

public class JsonWriter {
    public JsonWriter() {
        super();
    }

    public static void writeDeck(Deck deck, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(deck);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // write string json to file path
    }
}
