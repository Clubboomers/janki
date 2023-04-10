package Main.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    // identification for what type of card it is
    private CardType cardType;
    private Field[] fields;
    private long interval; // milliseconds, uses unix time
    private long due; // date in unix time
    private long created; // date in unix time

    public Card(CardType type, Field[] fields) {
        this.cardType = type;
        this.fields = fields;
        this.created = System.currentTimeMillis();
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType type) {
        this.cardType = type;
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
        this.due = created + interval;
    }

    public long getDue() {
        return due;
    }
}
