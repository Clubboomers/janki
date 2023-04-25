package main.utility;

import main.data.Card;
import main.data.Deck;

public class CardGrader {
    private Deck deck;
    public CardGrader(Deck deck) {
        this.deck = deck;
    }

    /**
     * Graduates a card from the learning phase to the review phase / ready phase / graduated phase
     * A new card will be moved from newCards to learningCards (as it is now in the learning phase)
     * A learning card will be moved from learningCards to graduatedCards (if it passes all the learning phases)
     * @param card The card to graduate
     */
    public void pass(Card card) {
        //Scheduler.pass(this, card); // TODO: implement scheduler
        int step = card.getLearningPhase();
        System.out.println("Step for card " + card.getCardId() + ": " + step);
        if (step < 0) { // if the card is in no learning phase yet (-1)
            if (deck.getGraduatedCards().contains(card)) {
                card.increasePassStreak();
                graduate(card);
                return;
            }
            card.setReady(false);
            card.startLearningPhase(); // sets the card to learning phase 0 (from -1)
            card.startCardThread(deck); // postpones the card for a specified amount of time
            deck.getLearningCards().add(card);
            if (deck.getNewCards().contains(card)) {      //
                deck.getNewCards().remove(card);         // moves the card to the learning phase
                try  {                                  //
                    deck.getDueCards().remove();
                } catch (Exception e) {
                    throw new IllegalStateException("Expected card " + card.getCardId() + " to be in due cards");
                }
            }


            System.out.println("Starting learning phase for card " + card.getCardId());
        } else if (step < deck.getOptions().getLearningSteps().length-1) { // if the card is in the learning phase
            card.setReady(false);
            card.increaseLearningPhase();
            card.startCardThread(deck);
            Card temp = deck.getLearningCards().poll();
            deck.getLearningCards().add(temp);
            System.out.println("Upgrading card " + card.getCardId() + " to learning phase " + card.getLearningPhase());
        } else if (step == deck.getOptions().getLearningSteps().length-1) { // if the card has finished the learning phase
            graduate(card);
        }
        else {
            throw new IllegalStateException("Card " + card + " is in an invalid learning phase");
        }
    }

    /**
     * Used to move cards from the learning state to the learned state (graduated cards)
     * @param card
     */
    public void graduate(Card card) {
        card.stopCardThread();
        card.setLastReviewed(System.currentTimeMillis());
        System.out.println("Upgrading card " + card.getCardId() + " to learned");
        deck.getLearningCards().remove(card);
        deck.getDueCards().remove(card);
        System.out.println("Cards in due cards: " + deck.getDueCards().size());
        if (deck.getGraduatedCards().contains(card)) {
            deck.getGraduatedCards().remove(card);
            deck.getGraduatedCards().add(card);
        }
        card.resetLearningPhase();
        Scheduler.pass(deck, card);
        // TODO: add a way to tell if a card failed once before passing
        if (card.getPassStreak() < 1) {
            card.addReviewHistory(System.currentTimeMillis(), true);
            card.increaseFailureStreak();
        }
        deck.getCards().set(deck.getCards().indexOf(card), card);
    }

    public void fail(Card card) {
        if (deck.getGraduatedCards().contains(card)) {
            card.startLearningPhase();
            deck.getLearningCards().add(deck.getGraduatedCards().poll());
            Scheduler.fail(deck, card);
        } else if (deck.getNewCards().contains(card)) {
            card.startLearningPhase();
            deck.getLearningCards().add(deck.getNewCards().poll());
            Scheduler.fail(deck, card);
        } else if (deck.getLearningCards().contains(card)) {
            card.decreaseLearningPhase();
            Scheduler.fail(deck, card);
        }
        else {
            throw new IllegalStateException("Card " + card + " is illegal");
        }
    }
}
