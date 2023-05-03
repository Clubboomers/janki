package main.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class CardList {
    public static final int NEW_CARDS = 0;
    public static final int LEARNING_CARDS = 1;
    public static final int GRADUATED_CARDS = 2;
    private int reviewOrder;
    private ArrayList<Card> cards; // keeps track of all cards in the deck
    private int cardCount;
    private Queue[] queues; // keeps track of all queues in the deck
    // below are lists of cards that are used for review
    private Queue<Card> newCards; // list of cards that are new TODO: implement
    private Queue<Card> graduatedCards; // list of cards that have passed the learning phase
    private Queue<Card> dueCards; // list of cards that are due to be reviewed
    private Queue<Card> learningCards; // list of cards that are in the learning phase
    private Queue<Card> readyCards; // list of cards that have matured after a review
    private int dueCardsCount;

    public CardList() {
        // empty constructor for Jackson deserialization
    }

    public CardList(int reviewOrder) {
        this.reviewOrder = reviewOrder;
        this.cards = new ArrayList<>();
        this.dueCards = new LinkedList<>();
        this.newCards = new LinkedList<>();
        this.learningCards = new LinkedList<>();
        this.readyCards = new LinkedList<>();
        this.graduatedCards = new LinkedList<>();
        this.queues = new Queue[]{newCards, graduatedCards, dueCards, learningCards, readyCards};
        this.cardCount = 0;
        this.dueCardsCount = 0;
    }

    public CardList(@JsonProperty("reviewOrder") int reviewOrder,
                    @JsonProperty("cards") ArrayList<Card> cards,
                    @JsonProperty("newCards") Queue<Card> newCards,
                    @JsonProperty("graduatedCards") Queue<Card> graduatedCards,
                    @JsonProperty("dueCards") Queue<Card> dueCards,
                    @JsonProperty("learningCards") Queue<Card> learningCards,
                    @JsonProperty("readyCards") Queue<Card> readyCards) {
        this.reviewOrder = reviewOrder;
        this.cards = cards;
        this.newCards = newCards;
        this.graduatedCards = graduatedCards;
        this.dueCards = dueCards;
        this.learningCards = learningCards;
        this.readyCards = readyCards;
        this.dueCardsCount = dueCards.size();
        this.cardCount = cards.size();
        queues = new Queue[]{newCards, graduatedCards, dueCards, learningCards, readyCards};
    }

    public void setQueues(Queue[] queues) {
        this.queues = queues;
    }

    public void setNewCards(Queue<Card> newCards) {
        this.newCards = newCards;
    }

    public void setGraduatedCards(Queue<Card> graduatedCards) {
        this.graduatedCards = graduatedCards;
    }

    public void setLearningCards(Queue<Card> learningCards) {
        this.learningCards = learningCards;
    }

    public void setReadyCards(Queue<Card> readyCards) {
        this.readyCards = readyCards;
    }

    public void setDueCards(Queue<Card> dueCards) {
        this.dueCards = dueCards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
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

    @JsonIgnore
    public void addCard(Card card) {
        cards.add(card);
        newCards.add(card);
        cardCount++;
    }

    @JsonIgnore
    public void deleteCard(Card card) {
        cards.remove(card);
        for (Queue queue : queues) {
            if (queue.contains(card)) {
                queue.remove(card);
            }
        }
        updateCounts();
        updateNewCards();
    }

    @JsonIgnore
    public void updateDueCards() {
        ArrayList<Card> dueCardsList = new ArrayList<>();
        for (Card card : cards) {
            if (card.isDue()) {
                dueCardsList.add(card);
            }
        }
        dueCardsCount = dueCardsList.size();
        dueCards.clear();
        dueCards.addAll(dueCardsList);
    }

    /**
     * This is a botched solution to the problem of new cards
     * not being removed from the newCards queue when they are
     * deleted from the deck. This method is called whenever
     * a card is deleted from the deck.
     * TODO: fix this bug
     */
    private void updateNewCards() {
        ArrayList<Card> newCardsList = new ArrayList<>();
        for (Card card : cards) {
            if (card.getLastReviewed() == 0) {
                newCardsList.add(card);
            }
        }
        newCards.clear();
        newCards.addAll(newCardsList);
    }

    @JsonIgnore
    private void updateCounts() {
        dueCardsCount = dueCards.size();
        cardCount = cards.size();
    }

    @JsonIgnore
    public void sortDueCards() {
        switch (reviewOrder) {
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

    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
    public Card getNextCardInReview() {
        switch (reviewOrder) {
            case DeckOptions.NEW_CARDS_FIRST:
                return getNextCardInReviewNewCardsFirst();
            case DeckOptions.ORDER_BY_RANDOM:
                return getNextCardInReviewOrderByRandom();
            case DeckOptions.NEW_CARDS_LAST:
                return getNextCardInReviewNewCardsLast();
            default:
                throw new IllegalStateException("Unexpected value in deck review order: " + reviewOrder);
        }
    }

    @JsonIgnore
    private Card getNextCardInReviewNewCardsLast() {
        // TODO: implement
        Card card;
        sortDueCardsNewCardsLast();
        card = dueCards.peek();
        return card;
    }

    @JsonIgnore
    private Card getNextCardInReviewOrderByRandom() {
        // TODO: implement
        Card card;
        sortDueCardsOrderByRandom();
        card = dueCards.peek();
        return card;
    }

    @JsonIgnore
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

    @JsonIgnore
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

    private void renameCardTypeUpdateQueues(String cardTypeOldName, String cardTypeNewName, Queue<Card> queue) {
        Queue<Card> tempQueue = new LinkedList<>();
        while (!queue.isEmpty()) {
            Card card = queue.poll();
            if (card.getCardType().equals(cardTypeOldName)) {
                card.setCardType(cardTypeNewName);
            }
            tempQueue.offer(card);
        }
        queue.addAll(tempQueue);
    }

    public void renameCardType(String cardTypeOldName, String cardTypeNewName) {
        for (Card card : cards) {
            if (card.getCardType().equals(cardTypeOldName)) {
                card.setCardType(cardTypeNewName);
            }
        }
        for (Queue<Card> queue : queues) {
            renameCardTypeUpdateQueues(cardTypeOldName, cardTypeNewName, queue);
        }
    }

    public void updateCardType(CardType cardType) {
    }
}
