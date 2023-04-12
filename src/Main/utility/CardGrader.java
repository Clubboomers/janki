package Main.utility;

import Main.data.Card;
import Main.data.Deck;

public class CardGrader {
    private Deck deck;
    public CardGrader(Deck deck) {
        this.deck = deck;
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
            deck.addUpdateThread(card);
            deck.getLearningCards().add(card);
            deck.getDueCards().remove(card);
            System.out.println("Starting learning phase for card " + card.getCardId());
        } else if (step < deck.getOptions().getLearningSteps().length-1) { // if the card is in the learning phase
            card.setReady(false);
            card.increaseLearningPhase();
            deck.addUpdateThread(card);
            Card temp = deck.getLearningCards().poll();
            deck.getLearningCards().add(temp);
            System.out.println("Upgrading card " + card.getCardId() + " to learning phase " + card.getLearningPhase());
        } else if (step == deck.getOptions().getLearningSteps().length-1) { // if the card has finished the learning phase
            //card.setInterval(Scheduler.pass(card.getInterval())); TODO: implement scheduler
            System.out.println("Upgrading card " + card.getCardId() + " to learned");
            deck.getLearningCards().remove(card);
            card.resetLearningPhase();
            deck.getCards().set(deck.getCards().indexOf(card), card);
        }
        else {
            throw new IllegalStateException("Card " + card + " is in an invalid learning phase");
        }
    }
}
