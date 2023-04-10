package Main.Data;

import java.io.Serializable;

/**
 * A Field is a single piece of information on a card.
 * Can be a word, a sentence, a picture, etc.
 */
public class Field {

    public static final boolean FRONT = true;
    public static final boolean BACK = false;
    private String name;
    private Object value;

    public Field(String name) {
        this.name = name;
    }

    /**
     * Creates a new field with the given name and value.
     * @param name The name of the field.
     * @param value What the field contains.
     */
    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
