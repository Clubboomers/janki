package main.data;

import main.utility.CardGrader;

import java.util.*;

public class Deck {
    private DeckOptions options;
    private CardList cardList;
    private CardGrader grader;
    private String name;

    public Deck(String name) {
        this.name = name;
        cardList = new CardList(this);
        options = new DeckOptions();
        grader = new CardGrader(this);
    }

    public int getNewCardCount() {
        return cardList.getNewCards().size();
    }

    public Queue<Card> getNewCards() {
        return cardList.getNewCards();
    }

    public int getGraduatedCardCount() {
        return cardList.getGraduatedCards().size();
    }

    public int getLearningCardCount() {
        return cardList.getLearningCards().size();
    }

    public DeckOptions getOptions() {
        return options;
    }

    public void setOptions(DeckOptions options) {
        this.options = options;
    }

    public void updateDueCards() {
        cardList.updateDueCards();
    }

    public int getDueCardsCount() {
        return cardList.getDueCardsCount();
    }

    public ArrayList<Card> getCards() {
        return cardList.getCards();
    }

    public Card getCard(int index) {
        return cardList.getCards().get(index);
    }

    public boolean finishedReview() {
        // if dueCards is empty, and learningCards is empty, and readyCards is empty
        System.out.println("Due cards are empty: " + cardList.getDueCards().isEmpty());
        System.out.println("Learning cards are empty: " + cardList.getLearningCards().isEmpty());
        System.out.println("New cards are empty: " + cardList.getNewCards().isEmpty());
        return ((cardList.getDueCards().isEmpty() && cardList.getLearningCards().isEmpty()) && cardList.getNewCards().isEmpty());
    }

    public Queue<Card> getGraduatedCards() {
        return cardList.getGraduatedCards();
    }

    public Queue<Card> getDueCards() {
        return cardList.getDueCards();
    }

    public Queue<Card> getLearningCards() {
        return cardList.getLearningCards();
    }

    public Queue<Card> getReadyCards() {
        return cardList.getReadyCards();
    }

    public void pass(Card card) {
        grader.pass(card);
    }

    public void fail(Card card) {
        grader.fail(card);
    }

    public int whatDeckIsThisCardIn(Card card) {
        return cardList.whatDeckIsThisCardIn(card);
    }


    /**
     * Uses a thread with a timer to update the due cards and learning cards list
     * by adding cards that are ready to be reviewed to the ready cards list.
     * The card that graduated the longest time ago is the next card to be reviewed.
     * @return The next card to be reviewed
     */
    public Card getNextCardInReview() { // TODO: make it use peek instead of poll in case the user exits mid-review
        return cardList.getNextCardInReview();
    }

    public void addToReadyCards(Card card) {
        cardList.getReadyCards().add(card);
    }

    public void addCard(Card card) {
        cardList.addCard(card);
    }

    public void deleteCard(Card card) {
        cardList.deleteCard(card);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCardCount() {
        return cardList.getCardCount();
    }
}