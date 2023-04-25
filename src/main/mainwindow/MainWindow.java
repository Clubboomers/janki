package main.mainwindow;

import javax.swing.*;

import main.MainWindowSaveLoader;
import main.browser.BrowserWindow;
import main.cardreviewer.CardReviewView;
import main.data.Card;
import main.data.CardType;
import main.data.Deck;
import main.data.Field;

import java.util.ArrayList;

public class MainWindow extends JFrame {
    private ArrayList<Deck> decks;
    private ArrayList<CardType> cardTypes;
    private MainWindowView view;

    /**
     * Pretty much the central class of the program. Contains all important data and methods.
     */
    public MainWindow() {
        super("SRS");
        decks = new ArrayList<Deck>();
        cardTypes = new ArrayList<CardType>();
        view = new MainWindowView(this);
        this.setContentPane(view);
        MWMenuBar menuBar = new MWMenuBar(this);
        this.setJMenuBar(menuBar);

        if (MainWindowSaveLoader.saveExists()) {
            try {
                load();
            } catch (Exception e) {
                // ask user if they want to create a new save
                int result = JOptionPane.showConfirmDialog(null, "An error occurred while loading the save file. Would you like to create a new save file?", "Error", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    init();
                } else {
                    System.exit(0);
                }
            }
        } else {
            init();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        // Save on exit
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                save();
            }
        });

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
        cardTypes.add(new CardType(name));
    }

    public void renameCardType(String oldName, String newName) {
        if (cardTypeExists(newName)) {
            JOptionPane.showMessageDialog(null, "Card type with name " + "\"" + newName + "\"" + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (newName.equals("") || newName == null) {
            JOptionPane.showMessageDialog(null, "Card type name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (CardType cardType : cardTypes) {
            if (cardType.getName().equals(oldName)) {
                cardType.setName(newName);
                updateAllCardsCardTypeName(oldName, newName);
                break;
            }
        }
    }

    public void removeCardTypeWithName(String name) {
        CardType cardType = getCardTypeWithName(name);
        cardTypes.remove(cardType);
        removeCardsWithType(cardType);
    }

    public void removeCardsWithType(CardType cardType) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardType().equals(cardType)) {
                    deck.deleteCard(card);
                    updateView();
                }
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

    /**
     * Get card type by using string.
     * @param name string to search for
     * @return CardType with matching name, null if not found
     */
    public CardType getCardTypeWithName(String name) {
        for (CardType cardType : cardTypes) {
            if (cardType.getName().equals(name)) {
                return cardType;
            }
        }
        return null;
    }

    /**
     * Used to check if any card with specific type exists.
     * @param cardType
     * @return true if card with type exists, false otherwise
     */
    public boolean cardWithCardTypeExists(CardType cardType) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardType().equals(cardType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getCardsWithCardType(CardType cardType) {
        int count = 0;
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardType().equals(cardType)) {
                    count++;
                }
            }
        }
        return count;
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
                if (card.getCardType().getName().equals(cardType.getName())) {
                    card.setCardType(cardType);
                }
            }
        }
    }

    /**
     * Same as above, but only used for renaming card types.
     * Since changing the name doesn't allow to use the above method,
     * this method is used instead.
     * @param cardTypeOldName old name of card type
     * @param cardTypeNewName new name of card type
     */
    public void updateAllCardsCardTypeName(String cardTypeOldName, String cardTypeNewName) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardType().getName().equals(cardTypeOldName)) {
                    card.getCardType().setName(cardTypeNewName);
                }
            }
        }
    }

    public Card getCard(int cardID) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardId() == cardID) {
                    return card;
                }
            }
        }
        return null;
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
        decks = new ArrayList<Deck>();
        cardTypes = new ArrayList<CardType>();
        view = new MainWindowView(this);
        for (int i = 0; i < 3; i++) {
            decks.add(new Deck("Deck " + i));
        }
        cardTypes.add(new CardType("Default", new Field[] {
                new Field("Front"),
                new Field("Back")
        }));
        cardTypes.add(new CardType("Test", new Field[] {
                new Field("Front"),
                new Field("Back"),
                new Field("Extra")
        }));
        decks.get(0).addCard(new Card(cardTypes.get(0), new Field[]{
                new Field("Front", "Din"),
                new Field("Back", "MAMMA!!")
        }));
        updateView();
    }

    public void updateView() {
        for (Deck deck : decks) {
            deck.updateDueCards();
        }
        view.update();
    }

    public void study() {
        Deck selectedDeck = getDeckWithName(MainContentView.getSelectedDeck());
        if (selectedDeck == null) {
            JOptionPane.showMessageDialog(null, "No deck selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (selectedDeck.getDueCardsCount() < 1)
            JOptionPane.showMessageDialog(null, "No due cards in deck.", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            System.out.println(selectedDeck.getName() + " selected for study.");
            this.setContentPane(new CardReviewView(this, selectedDeck));
        }
    }

    public void showHome() {
        this.setContentPane(view);
        updateView();
    }

    /**
     * Save all data to file.
     */
    public void save() {
        // TODO: Implement
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
        updateView();
    }

    public void showBrowse() {
        new BrowserWindow(this);
    }

    public void deleteCard(int cardID) {
        for (Deck deck : decks) {
            for (Card card : deck.getCards()) {
                if (card.getCardId() == cardID) {
                    deck.deleteCard(card);
                    return;
                }
            }
        }
    }
}
