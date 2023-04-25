package main.utility;

import main.data.Card;
import main.data.Deck;

public class Scheduler {
    public static void pass(Deck deck, Card card) {
        double passMultiplier = deck.getOptions().getPassMultiplier();
        card.setInterval((long) (card.getInterval() * passMultiplier));
    }

    public static void fail(Deck deck, Card card) {
        double failMultiplier = deck.getOptions().getFailMultiplier();
        card.setInterval((long) (card.getInterval() * failMultiplier));
    }
}
