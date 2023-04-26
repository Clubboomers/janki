package main.data;

/**
 * A Field is a single piece of information on a card.
 * Can be a word, a sentence, a picture, etc.
 */
public class Field {

    public static final boolean FRONT = true;
    public static final boolean BACK = false;
    private String name;
    private String content; // TODO: add support for images and audio

    public Field(String name) {
        this.name = name;
    }

    /**
     * Creates a new field with the given name and value.
     * @param name The name of the field.
     * @param content What the field contains.
     */
    public Field(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", value=" + content +
                '}';
    }
}
