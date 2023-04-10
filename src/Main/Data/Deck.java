package Main.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private String name;
    private int cardCount;
    private int dueCardsCount;

    @Override
    public String toString() {
        return cards + "###" + name + "###" + cardCount + "###" + dueCardsCount;
    }

    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
        cardCount = 0;
    }

    public int getDueCardsCount() {
        return dueCardsCount;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void addCard(Card card) {
        cards.add(card);
        cardCount++;
    }

    public void removeCard(Card card) {
        cards.remove(card);
        cardCount--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCardCount() {
        return cardCount;
    }
}
