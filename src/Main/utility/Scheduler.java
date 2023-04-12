package Main.utility;

import Main.data.Card;
import Main.data.Deck;

public class Scheduler {
    public static void pass(Deck deck, Card card) {
        double intervalMultiplier = deck.getOptions().getPassMultiplier();
        card.setInterval((long) (card.getInterval() * intervalMultiplier));
    }

    public static void fail(Deck deck, Card card) {
        double intervalMultiplier = deck.getOptions().getFailMultiplier();
        card.setInterval((long) (card.getInterval() * intervalMultiplier));
    }
}
