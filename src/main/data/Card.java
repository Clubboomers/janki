package main.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.utility.CardThread;
import main.utility.Intervals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Card {
    // TODO: add ID to keep track of cards
    private static int id = 0;
    private int cardId;
    // identification for what type of card it is
    private CardType cardType;
    private Field[] fields;
    private long created; // date in unix time
    private long interval; // milliseconds, uses unix time
    @JsonIgnore
    private long lastReviewed; // date in unix time
    private LinkedHashMap<Long, Boolean> reviewHistory; // dates of past reviews, boolean for pass/fail
    private long due; // date when card is due to be reviewed
    @JsonIgnore
    private int passStreak = 0; // number of consecutive correct answers
    @JsonIgnore
    private int failureStreak = 0; // number of consecutive incorrect answers, remove card after too many failures in a row
    private CardThread cardThread;
    private int learningPhase; // -1 = not in learning phase, 0 = learning phase 1, 1 = learning phase 2, 2 = learning phase 3, etc.
    private boolean isReady = false; // true if card is ready to be reviewed

    public static void setCardId(int id) {
        Card.id = id;
    }

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

    public Card() {
        // empty constructor for Jackson deserialization
    }

    public Card(CardType type, Field[] fields) {
        this.cardType = type;
        this.fields = fields;
        this.created = System.currentTimeMillis();
        this.interval = Intervals.ONE_DAY;
        this.due = created; // due immediately since it's new TODO: change this
        this.reviewHistory = new LinkedHashMap<>();
        this.cardId = id;
        this.learningPhase = -1;
        this.passStreak = 0;
        this.isReady = true;
        id++; // Cheap way to keep track of cards, increment ID for next card
    }

    @JsonCreator
    public Card(
            @JsonProperty("cardType")CardType cardType,
            @JsonProperty("fields") Field[] fields,
            @JsonProperty("created") long created,
            @JsonProperty("interval") long interval,
            @JsonProperty("reviewHistory") LinkedHashMap<Long, Boolean> reviewHistory,
            @JsonProperty("due") long due,
            @JsonProperty("cardId") int cardId,
            @JsonProperty("learningPhase") int learningPhase,
            @JsonProperty("isReady") boolean isReady,
            @JsonProperty("id") int id)
    {
        this.cardType = cardType;
        this.fields = fields;
        this.created = created;
        this.interval = interval;
        this.reviewHistory = reviewHistory;
        this.due = due;
        this.cardId = cardId;
        this.learningPhase = learningPhase;
        this.isReady = isReady;
        initFromHashmap();
    }

    /**
     * Initializes the pass/fail streaks from the review history.
     * This is only used when deserializing from JSON.
     * With this you don't have to write the streaks to JSON. (They can be calculated from the review history)
     */
    private void initFromHashmap() {
        if (reviewHistory == null) {
            reviewHistory = new LinkedHashMap<>();
        }
        this.lastReviewed = reviewHistory.keySet().stream().max(Long::compareTo).orElse(0L);
        this.passStreak = 0;
        this.failureStreak = 0;
        for (HashMap.Entry<Long, Boolean> entry : reviewHistory.entrySet()) {
            if (entry.getValue()) {
                passStreak++;
                failureStreak = 0;
            } else {
                failureStreak++;
                passStreak = 0;
            }
        }
    }

    /**
     * Only used for when creating new cards (that have no field values yet)
     * @param type The type of card to create
     */
    public Card(CardType type) {
        this.cardType = type;
        this.fields = type.getFields().clone(); // clone the fields so the card type doesn't get modified
    }

    public Card(long created, long interval, long lastReviewed, long due, CardType type, Field[] fields) {
        this.created = created;
        this.interval = Intervals.ONE_DAY;
        this.due = created;
        this.reviewHistory = new LinkedHashMap<>();
        this.cardType = type;
        this.fields = fields;
        this.cardId = id;
        this.learningPhase = -1;
        this.passStreak = 0;
        this.isReady = true;
        id++;
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

    public String getFieldContentByName(String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getContent();
            }
        }
        return null;
    }

    public void setFieldContentByName(String fieldName, String content) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setContent(content);
            }
        }
    }

    public String getFieldValue(String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getContent();
            }
        }
        return null;
    }

    @JsonIgnore
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
}
