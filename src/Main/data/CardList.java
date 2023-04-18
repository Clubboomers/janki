package Main.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class CardList {
    public static final int NEW_CARDS = 0;
    public static final int LEARNING_CARDS = 1;
    public static final int GRADUATED_CARDS = 2;
    private Deck parentDeck;
    private ArrayList<Card> cards; // keeps track of all cards in the deck
    private int cardCount;
    private Queue[] queues; // keeps track of all queues in the deck
    // below are lists of cards that are used for review
    private Queue<Card> newCards; // list of cards that are new TODO: implement
    private Queue<Card> graduatedCards; // list of cards that have passed the learning phase
    private Queue<Card> dueCards; // list of cards that are due to be reviewed
    private Queue<Card> learningCards; // list of cards that are in the learning phase
    private Queue<Card> readyCards; // list of cards that have matured after a review
    private int dueCardsCount = 0;

    public CardList(Deck parentDeck) {
        this.parentDeck = parentDeck;
        cards = new ArrayList<Card>();
        dueCards = new LinkedList<>();
        newCards = new LinkedList<>();
        learningCards = new LinkedList<>();
        readyCards = new LinkedList<>();
        graduatedCards = new LinkedList<>();
        queues = new Queue[]{newCards, graduatedCards, dueCards, learningCards, readyCards};
        cardCount = 0;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardCount() {
        return cardCount;
    }

    public Queue<Card> getNewCards() {
        return newCards;
    }

    public Queue<Card> getGraduatedCards() {
        return graduatedCards;
    }

    public Queue<Card> getDueCards() {
        return dueCards;
    }

    public int getDueCardsCount() {
        return dueCardsCount;
    }

    public Queue<Card> getLearningCards() {
        return learningCards;
    }

    public Queue<Card> getReadyCards() {
        return readyCards;
    }

    public void addCard(Card card) {
        cards.add(card);
        newCards.add(card);
        cardCount++;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
        for (Queue queue : queues) {
            if (queue.contains(card)) {
                queue.remove(card);
            }
        }
        cardCount--;
    }

    public Card[] updateDueCards() {
        ArrayList<Card> dueCardsList = new ArrayList<>();
        for (Card card : cards) {
            if (card.isDue()) {
                dueCardsList.add(card);
            }
        }
        dueCardsCount = dueCardsList.size();
        dueCards.clear();
        dueCards.addAll(dueCardsList);
        return dueCardsList.toArray(new Card[0]);
    }

    public void sortDueCards() {
        switch (parentDeck.getOptions().getReviewOrder()) {
            case DeckOptions.NEW_CARDS_FIRST:
                sortDueCardsNewCardsFirst();
                break;
            case DeckOptions.ORDER_BY_RANDOM:
                sortDueCardsOrderByRandom();
                break;
            case DeckOptions.NEW_CARDS_LAST:
                sortDueCardsNewCardsLast();
                break;
        }
    }

    private void sortDueCardsNewCardsLast() {
        // Create temporary list of cards
        ArrayList<Card> tempCards = new ArrayList<>();
        // Add due cards in order
        for (Card card : dueCards) {
            if (!newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Add new cards
        for (Card card : dueCards) {
            if (newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Clear due cards and add cards in right order
        dueCards.clear();
        dueCards.addAll(tempCards);
    }

    private void sortDueCardsOrderByRandom() {
        // Create temporary list of cards in right order
        ArrayList<Card> tempCards = new ArrayList<>();
        // Add new cards in order
        for (Card card : dueCards) {
            if (newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Add rest of due cards
        for (Card card : dueCards) {
            if (!newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Shuffle cards
        Collections.shuffle(tempCards);
        // Clear due cards and add cards in right order
        dueCards.clear();
        dueCards.addAll(tempCards);
    }

    private void sortDueCardsNewCardsFirst() {
        // Create temporary list of cards in right order
        ArrayList<Card> tempCards = new ArrayList<>();
        // Add new cards in order
        for (Card card : dueCards) {
            if (newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Add rest of due cards
        for (Card card : dueCards) {
            if (!newCards.contains(card)) {
                tempCards.add(card);
            }
        }
        // Clear due cards and add cards in right order
        dueCards.clear();
        dueCards.addAll(tempCards);
    }

    public Card getNextCardInReview() {
        switch (parentDeck.getOptions().getReviewOrder()) {
            case DeckOptions.NEW_CARDS_FIRST:
                return getNextCardInReviewNewCardsFirst();
            case DeckOptions.ORDER_BY_RANDOM:
                return getNextCardInReviewOrderByRandom();
            case DeckOptions.NEW_CARDS_LAST:
                return getNextCardInReviewNewCardsLast();
            default:
                throw new IllegalStateException("Unexpected value in deck review order: " + parentDeck.getOptions().getReviewOrder());
        }
    }

    private Card getNextCardInReviewNewCardsLast() {
        // TODO: implement
        Card card;
        sortDueCardsNewCardsLast();
        card = dueCards.peek();
        return card;
    }

    private Card getNextCardInReviewOrderByRandom() {
        // TODO: implement
        Card card;
        sortDueCardsOrderByRandom();
        card = dueCards.peek();
        return card;
    }

    private Card getNextCardInReviewNewCardsFirst() {
        Card card;
        sortDueCardsNewCardsFirst();
        if (!readyCards.isEmpty()) {
            card = readyCards.peek();
            System.out.println("drawing card from ready cards");
            return card;
        }  else if (!dueCards.isEmpty()) {
            card = dueCards.peek();
            System.out.println("drawing card from due cards");
            return card;
        } else if (!learningCards.isEmpty()) { // No cards are fully mature yet, so grab the next best card
            card = learningCards.peek();
            System.out.println("drawing card from learning cards");
            return card;
        }
        else {
            throw new IllegalStateException("No cards could be found for review.");
        }
    }

    public int whatDeckIsThisCardIn(Card card) {
        if (newCards.contains(card)) {
            return NEW_CARDS;
        } else if (learningCards.contains(card)) {
            return LEARNING_CARDS;
        } else if (graduatedCards.contains(card)) {
            return GRADUATED_CARDS;
        } else {
            return -1;
        }
    }
}
