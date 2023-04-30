package main.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Field is a single piece of information on a card.
 * Can be a word, a sentence, a picture, etc.
 */
public class Field {

    public static final boolean FRONT = true;
    public static final boolean BACK = false;

    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content; // TODO: add support for images and audio

    public Field() {
        // empty constructor for Jackson deserialization
    }

    public Field(String name) {
        this.name = name;
        this.content = "";
    }

    /**
     * Creates a new field with the given name and value.
     * @param name The name of the field.
     * @param content What the field contains.
     */
    @JsonCreator
    public Field(@JsonProperty("name") String name, @JsonProperty("content") String content) {
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
