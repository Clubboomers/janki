package main.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.utility.CardGrader;

import java.util.*;

public class Deck {
    private DeckOptions deckOptions;
    private CardList cardList;
    private CardGrader grader;
    private String name;

    public Deck() {
        // empty constructor for Jackson deserialization
    }

    public Deck(String name) {
        this.name = name;
        deckOptions = new DeckOptions();
        cardList = new CardList(this.getDeckOptions().getReviewOrder());
        grader = new CardGrader(this);
    }

    public Deck(@JsonProperty("name") String name,
                @JsonProperty("deckOptions") DeckOptions deckOptions,
                @JsonProperty("cardList") CardList cardList)
    {
        this.name = name;
        this.deckOptions = deckOptions;
        this.cardList = cardList;
        this.grader = new CardGrader(this);
    }

    public CardList getCardList() {
        return cardList;
    }

    @JsonIgnore
    public int getNewCardsCount() {
        return cardList.getNewCards().size();
    }

    public void setCardList(CardList cardList) {
        this.cardList = cardList;
    }

    @JsonIgnore
    public Queue<Card> getNewCards() {
        return cardList.getNewCards();
    }

    @JsonIgnore
    public int getGraduatedCardCount() {
        return cardList.getGraduatedCards().size();
    }

    @JsonIgnore
    public int getLearningCardCount() {
        return cardList.getLearningCards().size();
    }

    public DeckOptions getDeckOptions() {
        return deckOptions;
    }

    public void applyOptions(ArrayList<Option> options) {
        deckOptions.setOptions(options);
    }

    public void setDeckOptions(DeckOptions deckOptions) {
        this.deckOptions = deckOptions;
    }

    public void updateDueCards() {
        cardList.updateDueCards();
    }

    @JsonIgnore
    public int getDueCardsCount() {
        return cardList.getDueCardsCount();
    }

    @JsonIgnore
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

    @JsonIgnore
    public Queue<Card> getGraduatedCards() {
        return cardList.getGraduatedCards();
    }

    @JsonIgnore
    public Queue<Card> getDueCards() {
        return cardList.getDueCards();
    }

    @JsonIgnore
    public Queue<Card> getLearningCards() {
        return cardList.getLearningCards();
    }

    @JsonIgnore
    public Queue<Card> getReadyCards() {
        return cardList.getReadyCards();
    }

    public void pass(Card card) {
        grader.pass(card);
    }

    public void fail(Card card) {
        grader.fail(card);
    }

    @JsonIgnore
    public int whatDeckIsThisCardIn(Card card) {
        return cardList.whatDeckIsThisCardIn(card);
    }


    /**
     * Uses a thread with a timer to update the due cards and learning cards list
     * by adding cards that are ready to be reviewed to the ready cards list.
     * The card that graduated the longest time ago is the next card to be reviewed.
     * @return The next card to be reviewed
     */
    @JsonIgnore
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

    @JsonIgnore
    public int getCardCount() {
        return cardList.getCardCount();
    }

    public void renameCardType(String cardTypeOldName, String cardTypeNewName) {
        cardList.renameCardType(cardTypeOldName, cardTypeNewName);
    }

    public void updateCardType(CardType cardType) {
        cardList.updateCardType(cardType);
    }
}
