package Main.data;

import Main.utility.CardThread;
import Main.utility.Intervals;

import java.util.Arrays;
import java.util.HashMap;

public class Card {
    // TODO: add ID to keep track of cards
    private static int ID = 0;
    private int cardId;
    // identification for what type of card it is
    private CardType cardType;
    private Field[] fields;
    private long created; // date in unix time
    private long interval; // milliseconds, uses unix time
    private long lastReviewed; // date in unix time
    private HashMap<Long, Boolean> reviewHistory; // dates of past reviews, boolean for pass/fail
    private long due; // date when card is due to be reviewed
    private int passStreak = 0; // number of consecutive correct answers
    private int failureStreak = 0; // number of consecutive incorrect answers, remove card after too many failures in a row
    private CardThread cardThread;

    public int getFailureStreak() {
        return failureStreak;
    }

    public void increaseFailureStreak() {
        failureStreak++;
    }

    public int getPassStreak() {
        return passStreak;
    }

    public void increasePassStreak() {
        passStreak++;
    }

    private int learningPhase; // -1 = not in learning phase, 0 = learning phase 1, 1 = learning phase 2, 2 = learning phase 3, etc.
    private boolean isReady = false; // true if card is ready to be reviewed

    @Override
    public String toString() {
        return "Card{" +
                "localID=" + cardId +
                ", cardType=" + cardType +
                ", fields=" + Arrays.toString(fields) +
                ", created=" + created +
                ", interval=" + interval +
                ", due=" + due +
                ", reviewHistory=" + reviewHistory +
                '}';
    }

    public Card(CardType type, Field[] fields) {
        this.cardType = type;
        this.fields = fields;
        this.created = System.currentTimeMillis();
        this.interval = Intervals.ONE_DAY;
        this.due = created; // due immediately since it's new TODO: change this
        this.reviewHistory = new HashMap<>();
        this.cardId = ID;
        this.learningPhase = -1;
        this.passStreak = 0;
        this.isReady = true;
        ID++; // Cheap way to keep track of cards, increment ID for next card
    }

    public Card(long created, long interval, long lastReviewed, long due, CardType type, Field[] fields) {
        this.created = created;
        this.interval = Intervals.ONE_DAY;
        this.due = created;
        this.reviewHistory = new HashMap<>();
        this.cardType = type;
        this.fields = fields;
        this.cardId = ID;
        this.learningPhase = -1;
        this.passStreak = 0;
        this.isReady = true;
        ID++;
    }

    public int getCardId() {
        return cardId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Field[] getFields() {
        return fields;
    }

    public Object getFieldValue(String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getValue();
            }
        }
        return null;
    }

    public String getSortField() {
        return cardType.getSortField().getName();
    }

    public long getCreated() {
        return created;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
        this.due = lastReviewed + interval;
    }

    public long getDue() {
        return due;
    }

    public long getLastReviewed() {
        return lastReviewed;
    }

    public int getLearningPhase() {
        return learningPhase;
    }

    public int increaseLearningPhase() {
        learningPhase++;
        return learningPhase;
    }

    public void setLastReviewed(long lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public int decreaseLearningPhase() {
        if (learningPhase > 0)
            learningPhase--;
        return learningPhase;
    }

    public void startLearningPhase() {
        learningPhase = 0;
    }

    public int resetLearningPhase() {
        learningPhase = -1;
        return learningPhase;
    }

    public void setReady(boolean b) {
        isReady = b;
    }

    public boolean isDue() { // TODO: fix so that all cards due before 24:00 this day are due
        return due <= System.currentTimeMillis();
    }

    public void addReviewHistory(long date, boolean pass) {
        reviewHistory.put(date, pass);
    }

    public void startCardThread(Deck deck) {
        boolean isAlive = false;
        try {
            isAlive = cardThread.isAlive();
        } catch (NullPointerException e) {
            // do nothing
        }
        if (isAlive) {
            cardThread.stopThread();
        }
        cardThread = new CardThread(deck, this);
        cardThread.startThread();
    }

    public void stopCardThread() {
        if (cardThread.isAlive()) {
            cardThread.stopThread();
        }
    }
}
