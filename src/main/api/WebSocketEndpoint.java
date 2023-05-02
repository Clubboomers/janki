package main.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.data.Deck;
import main.mainwindow.MainWindow;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Arrays;

import static main.api.Commands.*;

@ServerEndpoint("/flashcard")
public class WebSocketEndpoint {
    private Session session;
    private ObjectMapper objectMapper;
    private MainWindow mw;

    public WebSocketEndpoint(MainWindow mw) {
        this.mw = mw;
        objectMapper = new ObjectMapper();
        System.out.println("WebSocketEndpoint created");
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("WebSocket received message: " + message);

        if (this.session == null || !session.isOpen()) {
            System.out.println("WebSocket session is null or closed");
            return;
        }

        // create json object from message
        String json = objectMapper.createObjectNode().put("message", message).toString();
        // get the command from the json object
        String command = objectMapper.createObjectNode().put("message", message).get("command").asText();

        switch (command) {
            case ADD_DECK:
                addDeck(json, mw);
                break;
            case GET_DECK_NAMES:
                getDecks(json, mw);
                break;
        }
    }

    private void getDecks(String json, MainWindow mw) {
        System.out.println("getDecks called");
        // get the decks from the database
        String[] decks = mw.getDeckNames();
        // create json object from decks
        String jsonDecks = objectMapper.createObjectNode().put("decks", Arrays.toString(decks)).toString();
        // send json object to client
        try {
            session.getBasicRemote().sendText(jsonDecks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDeck(String json, MainWindow mw) {
        System.out.println("addDeck called");
        // get the deck name from the json object
        String deckName = objectMapper.createObjectNode().put("message", json).get("deckName").asText();
        // add the deck to the database
        mw.addDeck(new Deck(deckName));
    }

    @OnClose
    public void onClose() {
        System.out.println("WebSocket closed: " + session.getId());
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("WebSocket error: " + throwable.getMessage());
    }
}
