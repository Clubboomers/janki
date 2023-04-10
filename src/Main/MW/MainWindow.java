package Main.MW;

import javax.swing.*;
import javax.swing.border.Border;

import Main.MainWindowSaveLoader;
import Main.Browser.BrowserWindow;
import Main.CardReviewer.CardReviewView;
import Main.CardReviewer.CardReviewWindow;
import Main.Data.Card;
import Main.Data.CardType;
import Main.Data.Deck;
import Main.Data.Field;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Pretty much the central class of the program. Contains all important data and methods.
 */
public class MainWindow extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Deck> decks;
    private ArrayList<CardType> cardTypes; // TODO: Implement
    private MainWindowView view;
    public MainWindow() {
        super("SRS");
        decks = new ArrayList<Deck>();
        cardTypes = new ArrayList<CardType>();
        //init();
        view = new MainWindowView(this);
        this.setContentPane(view);
        MWMenuBar menuBar = new MWMenuBar(this);
        this.setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        // Load save file
        try {
            //load();
        } catch (Exception e) {
            // TODO: handle exception
        }

        init();

        setVisible(true);
    }

    /**
     * Returns all decks in the program.
     * @return ArrayList of decks
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getDeckWithName(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name)) {
                return deck;
            }
        }
        return null;
    }

    /**
     * Check for deck with specific name.
     * @param name search query
     * @return true if deck with name exists, false otherwise
     */
    public boolean deckExists(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove deck with specific name from list.
     * @param name deck with this name will be removed
     */
    public void removeDeckWithName(String name) {
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i).getName().equals(name)) {
                decks.remove(i);
                MainContentView.updateDeckList(this);
                break;
            }
        }
    }

    /**
     * Rename deck with specific name.
     * @param deckName deck to rename
     * @param newName new name for deck
     */
    public void renameDeckWithName(String deckName, String newName) {
        if (deckExists(newName)) {
            JOptionPane.showMessageDialog(null, "Deck with name " + "\"" + newName + "\"" + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (newName.equals("") || newName.equals(" ")) {
            JOptionPane.showMessageDialog(null, "Deck name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                deck.setName(newName);
                MainContentView.updateDeckList(this);
                break;
            }
        }
    }

    /**
     * Used for populating JList with all deck names
     * @return array of deck names
     */
    public String[] getDeckNames() {
        String[] deckNames = new String[decks.size()];
        for (int i = 0; i < decks.size(); i++) {
            deckNames[i] = decks.get(i).getName();
        }
        return deckNames;
    }

    public void addDeck(Deck deck) {
        if (deckExists(deck.getName())) {
            JOptionPane.showMessageDialog(null, "Deck with name " + "\"" + deck.getName() + "\"" + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        decks.add(deck);
        MainContentView.updateDeckList(this);
    }

    /**
     * Used for populating JList with all card type names
     * @return array of strings containing card type names
     */
    public String[] getCardTypeNames() {
        String[] cardTypeNames = new String[cardTypes.size()];
        for (int i = 0; i < cardTypes.size(); i++) {
            cardTypeNames[i] = cardTypes.get(i).getName();
        }
        return cardTypeNames;
    }

    public void addCardType(CardType cardType) {
        cardTypes.add(cardType);
    }

    public void addCardType(String name) {
        if (cardTypeExists(name)) {
            JOptionPane.showMessageDialog(null, "Card type with name " + "\"" + name + "\"" + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (name.equals("") || name == null) {
            JOptionPane.showMessageDialog(null, "Card type name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cardTypes.add(new CardType(name, new Field[0]));
    }

    public void renameCardType(String selectedValue, String newName) {
        if (cardTypeExists(newName)) {
            JOptionPane.showMessageDialog(null, "Card type with name " + "\"" + newName + "\"" + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (newName.equals("") || newName == null) {
            JOptionPane.showMessageDialog(null, "Card type name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (CardType cardType : cardTypes) {
            if (cardType.getName().equals(selectedValue)) {
                cardType.setName(newName);
                break;
            }
        }
    }

    public void removeCardTypeWithName(String name) {
        for (int i = 0; i < cardTypes.size(); i++) {
            if (cardTypes.get(i).getName().equals(name)) {
                cardTypes.remove(i);
                break;
            }
        }
    }

    public boolean cardTypeExists(String name) {
        for (CardType cardType : cardTypes) {
            if (cardType.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public CardType getCardTypeWithName(String name) {
        for (CardType cardType : cardTypes) {
            if (cardType.getName().equals(name)) {
                return cardType;
            }
        }
        return null;
    }

    public ArrayList<CardType> getCardTypes() {
        return cardTypes;
    }

    /**
     * Used to update all cards of specific type.
     * Used for updating the html of already created cards.
     * @param cardType card type to update
     */
    public void updateAllCards(CardType cardType) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardType().equals(cardType)) {
                    card.setCardType(cardType);
                }
            }
        }
    }

    /**
     * Used to get all cards in all decks.
     * @return array list of all cards stored in the program
     */
    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Deck deck : decks) {
            cards.addAll(deck.getCards());
        }
        return cards;
    }

    /**
     * Temporary method for populating decks and card types.
     * Used for testing.
     */
    private void init() {
        for (int i = 0; i < 3; i++) {
            decks.add(new Deck("Deck " + i));
        }
        cardTypes.add(new CardType("Default", new Field[]{
                new Field("Front"),
                new Field("Back")
        }));
        cardTypes.add(new CardType("Test", new Field[]{
                new Field("Front"),
                new Field("Back"),
                new Field("Extra")
        }));
        decks.get(0).addCard(new Card(cardTypes.get(0), new Field[]{
                new Field("Front", "Din"),
                new Field("Back", "MAMMA!!")
        }));
        view.update();
    }

    public void updateView() {
        view.update();
    }

    public void study() {
        Deck selectedDeck = getDeckWithName(MainContentView.getSelectedDeck());
        if (selectedDeck == null) {
            JOptionPane.showMessageDialog(null, "No deck selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println(selectedDeck.getName() + " selected for study.");
        this.setContentPane(new CardReviewView(this, selectedDeck));
    }

    public void showHome() {
        this.setContentPane(view);
    }

    /**
     * Save all data to file.
     */
    public void save() {
        // TODO: Implement
        //MainWindowSaveLoader.saveDecks(decks);
        MainWindowSaveLoader.saveDecks(decks);
        MainWindowSaveLoader.saveCardTypes(cardTypes);
    }

    /**
     * Load all data from file.
     */
    public void load() {
        // TODO: Implement
        decks = MainWindowSaveLoader.loadDecks();
        cardTypes = MainWindowSaveLoader.loadCardTypes();
        view.update();
    }

    public void showBrowse() {
        new BrowserWindow(this);
    }
}
