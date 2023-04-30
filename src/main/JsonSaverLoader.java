package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.data.CardType;
import main.data.Deck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonSaverLoader {
    public static boolean saveExists() {
        File cardTypes = new File("cardTypes.json");
        File decks = new File("decks.json");
        return cardTypes.exists() && decks.exists();
    }

    public static void saveDecks(ArrayList<Deck> decks) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("decks.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, decks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Deck> loadDecks() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("decks.json");
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Deck.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveCardTypes(ArrayList<CardType> cardTypes) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("cardTypes.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, cardTypes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<CardType> loadCardTypes() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("cardTypes.json");
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, CardType.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
