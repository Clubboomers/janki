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
     *
     * @param card The card to be set to ready
     * @param waitTimeMinutes The amount of time to wait before setting the card to ready (in minutes)
     */
    public CardThread(Deck deck, Card card, int waitTimeMinutes) {
        this.deck = deck;
        this.card = card;
        this.waitTime = waitTimeMinutes*Intervals.ONE_MINUTE;
        this.cardThread = new Thread(this);
        cardThread.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Card " + card.getCardId() + " will be ready for review in " + waitTime/Intervals.ONE_MINUTE + " minutes.");
            Thread.sleep(waitTime);
            card.setReady(true);
            deck.addToReadyCards(card);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

