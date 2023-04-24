package Main.utility;

import Main.data.Card;
import Main.data.Deck;

/**
 * This class is used to create a thread that will set a card to ready for review after a specified amount of time.
 * This is used to implement the learning phase of the algorithm.
 */
public class CardThread implements Runnable {
    private Deck deck;
    private Card card;
    private long waitTime; // in milliseconds
    private Thread cardThread;

    /**
     * Creates a new CardThread object and starts the thread
     * @param deck the deck that the card belongs to, has options that are used to determine the wait time
     * @param card the card to postpone
     */
    public CardThread(Deck deck, Card card) {
        this.deck = deck;
        this.card = card;
        int waitTimeMinutes = deck.getOptions().getLearningSteps()[card.getLearningPhase()];
        this.waitTime = waitTimeMinutes*Intervals.ONE_MINUTE;
        this.cardThread = new Thread(this);
    }

    public boolean isAlive() {
        return cardThread.isAlive();
    }

    public void startThread() {
        cardThread.start();
    }

    public void stopThread() {
        cardThread.interrupt();
        System.out.println("Card " + card.getCardId() + " thread stopped");
    }

    @Override
    public void run() {
        try {
            System.out.println("Card " + card.getCardId() + " will be ready for review in " + waitTime/Intervals.ONE_MINUTE + " minutes.");
            Thread.sleep(waitTime);
            card.setReady(true);
            deck.addToReadyCards(card);
            System.out.println("Card " + card.getCardId() + " is now ready for review.");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

