package Main.data;

import Main.utility.CardComparator;
import Main.utility.CardGrader;
import Main.utility.CardThread;

import java.util.*;

public class Deck {
    private DeckOptions options;
    private ArrayList<Card> cards; // list of all cards in the deck
    // below are lists of cards that are used for review
    private Queue<Card> graduatedCards; // list of cards that have passed the learning phase
    private Queue<Card> dueCards; // list of cards that are due to be reviewed
    private Queue<Card> learningCards; // list of cards that are in the learning phase
    private Queue<Card> readyCards; // list of cards that have matured after a review
    private CardGrader grader;
    private String name;
    private int cardCount;
    private int dueCardsCount;
    @Override
    public String toString() {
        return cards + "###" + name + "###" + cardCount + "###" + cardCount;
    }

    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
        dueCards = new LinkedList<>();
        options = new DeckOptions();
        learningCards = new LinkedList<>();
        readyCards = new LinkedList<>();
        graduatedCards = new LinkedList<>();
        grader = new CardGrader(this);
        cardCount = 0;
    }

    public DeckOptions getOptions() {
        return options;
    }

    public void setOptions(DeckOptions options) {
        this.options = options;
    }

    public void updateDueCards() {
        dueCards.clear();
        for (Card card : cards) {
            if (isDue(card)) {
                dueCards.add(card);
            }
        }
        dueCardsCount = dueCards.size();
    }

    private void sortDueCards() {
        // Sorting method
        int order = options.getOrder();
        List<Card> list = new ArrayList<>(this.dueCards); // convert queue to list
        if (order == DeckOptions.ORDER_BY_CREATED) {
            Collections.sort(list, new CardComparator(CardComparator.ORDER_BY_CREATED));
            dueCards = new LinkedList<>(list);
        } else if (order == DeckOptions.ORDER_BY_DUE) {
            Collections.sort(list, new CardComparator(CardComparator.ORDER_BY_DUE));
            dueCards = new LinkedList<>(list);
        } else if (order == DeckOptions.ORDER_BY_RANDOM) {
            Collections.shuffle(list);
            dueCards = new LinkedList<>(list);
        }
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

    public boolean finishedReview() {
        return (dueCards.isEmpty() && learningCards.isEmpty()) && readyCards.isEmpty(); // if dueCards is empty, and learningCards is empty, and readyCards is empty
    }

    public Queue<Card> getGraduatedCards() {
        return graduatedCards;
    }

    public Queue<Card> getDueCards() {
        return dueCards;
    }

    public Queue<Card> getLearningCards() {
        return learningCards;
    }

    public Queue<Card> getReadyCards() {
        return readyCards;
    }

    /**
     * Graduates a card from the learning phase to the review phase.
     * If the card has finished the learning phase, then it is removed from the learning lists
     * and the card in the original list is updated.
     * @param card The card to graduate
     */
    public void pass(Card card) {
        //Scheduler.pass(this, card); // TODO: implement scheduler
        int step = card.getLearningPhase();
        System.out.println(step);
        if (step < 0) { // if the card is in no learning phase yet (-1)
            card.setReady(false);
            card.startLearningPhase();
            addUpdateThread(card);
            learningCards.add(card);
            dueCards.remove(card);
            System.out.println("Starting learning phase for card " + card.getCardId());
        } else if (step < options.getLearningSteps().length-1) { // if the card is in the learning phase
            card.setReady(false);
            card.increaseLearningPhase();
            addUpdateThread(card);
            Card temp = learningCards.poll();
            learningCards.add(temp);
            System.out.println("Upgrading card " + card.getCardId() + " to learning phase " + card.getLearningPhase());
        } else if (step == options.getLearningSteps().length-1) { // if the card has finished the learning phase
            //card.setInterval(Scheduler.pass(card.getInterval())); TODO: implement scheduler
            System.out.println("Upgrading card " + card.getCardId() + " to learned");
            learningCards.remove(card);
            card.resetLearningPhase();
            cards.set(cards.indexOf(card), card);
        }
        else {
            throw new IllegalStateException("Card " + card + " is in an invalid learning phase");
        }
    }

    public void fail(Card card) {
        if (graduatedCards.contains(card)) {
            learningCards.add(graduatedCards.poll());
        }
    }

    /**
     * Uses a thread with a timer to update the due cards and learning cards list
     * by adding cards that are ready to be reviewed to the ready cards list.
     * The card that graduated the longest time ago is the next card to be reviewed.
     * @return The next card to be reviewed
     */
    public Card getNextCardInReview() { // TODO: make it use peek instead of poll in case the user exits mid-review
        Card card;
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

    /**
     * Will set the card to ready for review after the specified amount of time as specified in the options
     * @param card The card to update.
     */
    public void addUpdateThread(Card card) {
        int step = card.getLearningPhase();
        new CardThread(this, card, options.getLearningSteps()[step]);
    }

    public void addToReadyCards(Card card) {
        readyCards.add(card);
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

    private boolean isDue(Card card) {
        return card.getDue() < System.currentTimeMillis();
    }
}
