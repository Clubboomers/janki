package Main.utility;

import Main.data.Card;
import Main.data.DeckOptions;

import java.util.Comparator;

/**
 * This class is used to compare cards based on the order specified in DeckOptions.
 */
public class CardComparator implements Comparator<Card> {
    public static final int ORDER_BY_CREATED = 0; // Newest cards first
    public static final int ORDER_BY_RANDOM = 1; // Random order
    public static final int ORDER_BY_DUE = 2; // Oldest cards first
    private int order;
    public CardComparator(int order) {
        this.order = order;
    }
    @Override
    public int compare(Card c1, Card c2) {
        if (order == ORDER_BY_CREATED) {
            return compareByCreated(c1, c2);
        } else if (order == ORDER_BY_DUE) {
            return compareByDue(c1, c2);
        } else if (order == ORDER_BY_RANDOM) {
            return compareByRandom(c1, c2);
        }
        return 0;
    }

    private int compareByRandom(Card c1, Card c2) {
        return 0;
    }

    private int compareByDue(Card c1, Card c2) {
        if (c1.getDue() > c2.getDue()) {
            return 1;
        } else if (c1.getDue() < c2.getDue()) {
            return -1;
        }
        return 0;
    }

    private int compareByCreated(Card c1, Card c2) {
        if (c1.getCreated() > c2.getCreated()) {
            return 1;
        } else if (c1.getCreated() < c2.getCreated()) {
            return -1;
        }
        return 0;
    }

    @Override
    public Comparator<Card> reversed() {
        return Comparator.super.reversed();
    }
}
