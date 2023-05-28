package main.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.data.Deck;
import main.mainwindow.MainWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static main.api.Commands.*;

public class ClientManager implements Runnable {
    private Socket clientSocket;
    private MainWindow mw;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Thread clientThread;
    private ObjectMapper objectMapper;
    public ClientManager(Socket clientSocket, MainWindow mw) {
        this.clientSocket = clientSocket;
        this.mw = mw;
        try {
            streamIn = new DataInputStream(clientSocket.getInputStream());
            streamOut = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        objectMapper = new ObjectMapper();
        Server.incrementClientCount();
        clientThread = new Thread(this);
        clientThread.start();
        System.out.println("ClientManager created");
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Server listening for message...");
                byte[] buffer = new byte[1024];
                int bytesRead = streamIn.read(buffer);
                if (bytesRead == -1) {
                    System.out.println("Client closed connection");
                    this.close();
                    break;
                }
                String message = new String(buffer, 0, bytesRead).trim();
                handleMessage(message);
            } catch (IOException e) {
                this.close();
                break;
            }
        }
    }

    private void close() {
        try {
            streamIn.close();
            streamOut.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Server.decrementClientCount();
        System.out.println("ClientManager closed");
    }

    private void handleMessage(String message) {
        System.out.println("WebSocket received message: " + message);

        if (this.clientSocket == null || !clientSocket.isConnected()) {
            System.out.println("WebSocket session is null or closed");
            return;
        }

        // create json object from message
        JsonNode json = null;
        try {
            json = objectMapper.readTree(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // get the command from the json object
        String command = json.get("command").asText();

        switch (command) {
            case ADD_DECK:
                addDeck(json, mw);
                break;
            case GET_DECK_NAMES:
                getDeckNames(mw, streamOut);
                break;
            case GET_DECK:
                getDeck(json, mw);
                break;
            case GET_CARD_TYPE_NAMES:
                getCardTypeNames(mw, streamOut);
                break;
            case GET_CARD_TYPES:
                getCardTypes(mw, streamOut);
                break;
            case ADD_CARD:
                addCard(json, mw);
                break;
            case DELETE_CARD:
                deleteCard(json, mw);
                break;
            case EDIT_CARD:
                editCard(json, mw);
                break;
        }
    }

    private void getCardTypes(MainWindow mw, DataOutputStream streamOut) {
        System.out.println("getCardTypes called");
        // get the card types from the database
        ArrayList cardTypes = mw.getCardTypes();
        // create json object from card types
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardTypesJson = objectMapper.createObjectNode();
        cardTypesJson.put("command", GET_CARD_TYPES);
        cardTypesJson.put("cardTypes", objectMapper.valueToTree(cardTypes));

        // convert json object to string
        String cardTypesJsonString = cardTypesJson.toString();

        System.out.println(cardTypesJsonString);

        // send the card types to the client
        try {
            streamOut.writeUTF(cardTypesJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCardTypeNames(MainWindow mw, DataOutputStream streamOut) {
        System.out.println("getCardTypeNames called");
        // get the card types from the database
        String[] cardTypes = mw.getCardTypeNames();
        // create json object from card types
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardTypesJson = objectMapper.createObjectNode();
        cardTypesJson.put("command", GET_CARD_TYPE_NAMES);
        cardTypesJson.put("cardTypes", Arrays.toString(cardTypes));

        // convert json object to string
        String cardTypesJsonString = cardTypesJson.toString();

        System.out.println(cardTypesJsonString);

        // send the card types to the client
        try {
            streamOut.writeUTF(cardTypesJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void editCard(JsonNode json, MainWindow mw) {
    }

    private void deleteCard(JsonNode json, MainWindow mw) {
    }

    private void addCard(JsonNode json, MainWindow mw) {

    }

    private void getDeck(JsonNode json, MainWindow mw) {
    }

    private static String[] getDeckNames(MainWindow mw, DataOutputStream streamOut) {
        System.out.println("getDecks called");
        // get the decks from the database
        String[] decks = mw.getDeckNames();
        // create json object from decks
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode decksJson = objectMapper.createObjectNode();
        decksJson.put("command", GET_DECK_NAMES);
        decksJson.put("decks", Arrays.toString(decks));

        // convert json object to string
        String decksJsonString = decksJson.toString();

        System.out.println(decksJsonString);

        // send the decks to the client
        try {
            streamOut.writeUTF(decksJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return decks;
    }

    private void addDeck(JsonNode json, MainWindow mw) {
        System.out.println("add_deck called");
        // get the deck from the database
        String deckName = json.get("deckName").asText();
        mw.addDeck(new Deck(deckName));
        // create json object from deck
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode deckJson = objectMapper.createObjectNode();
        deckJson.put("command", GET_DECK);
        deckJson.put("deck", deckName);
        String deckJsonString = deckJson.toString();
        try {
            streamOut.writeUTF(deckJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
