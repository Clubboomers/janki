package main.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * A CardType is a template for a card. It tells the program
 * how many fields to expect on the front and back of a card.
 */
public class CardType {
    private String name;
    private Field[] fields;
    private Field sortField;
    private int totalFieldCount;
    private String htmlFront; // HTML for front side of card. Not directly set by the user.
    private String htmlBack; 
    private String css; // Styling for html. Set by the user.
    private String htmlBodyFront; // Code for the <body> tag of the front side. Set by the user.
    private String htmlBodyBack; // Back side <body> tag. Set by the user.

    public CardType() {
        // Empty constructor for JSON deserialization
    }

    /**
     * Creates a new CardType with the given name and fields.
     * Each field knows whether it is on the front or back of the card.
     * @param name The name of the card type.
     * @param fields The fields that make up the card.
     */
    public CardType(String name, Field[] fields) {
        this.name = name;
        this.fields = fields;
        this.totalFieldCount = fields.length;
        css = "";
        htmlBodyFront = "";
        htmlBodyBack = "";
        updateHtmlFront();
        updateHtmlBack();
        if (fields.length > 0) {
            sortField = fields[0];
        }
    }

    /**
     * Creates a new CardType with the given name and two fields.
     * @param name The name of the card type.
     */
    public CardType(String name) {
        this.name = name;
        this.fields = new Field[2];
        this.fields[0] = new Field("Front");
        this.fields[1] = new Field("Back");
        this.totalFieldCount = fields.length;
        css = "";
        htmlBodyFront = "";
        htmlBodyBack = "";
        updateHtmlFront();
        updateHtmlBack();
        if (fields.length > 0) {
            sortField = fields[0];
        }
    }


    /**
     * Used for loading card types from a file.
     * @param name
     * @param fields
     * @param htmlFront
     * @param htmlBack
     * @param css
     * @param htmlBodyFront
     * @param htmlBodyBack
     */
    public CardType(String name, Field[] fields, String htmlFront, String htmlBack, String css, String htmlBodyFront, String htmlBodyBack) {
        this.name = name;
        this.fields = fields;
        this.totalFieldCount = fields.length;
        this.htmlFront = htmlFront;
        this.htmlBack = htmlBack;
        this.css = css;
        this.htmlBodyFront = htmlBodyFront;
        this.htmlBodyBack = htmlBodyBack;
        if (fields.length > 0) {
            sortField = fields[0];
        }
    }

    @JsonCreator
    public CardType(@JsonProperty("name") String name,
                    @JsonProperty("fields") Field[] fields,
                    @JsonProperty("sortField") Field sortField,
                    @JsonProperty("css") String css,
                    @JsonProperty("htmlBodyFront") String htmlBodyFront,
                    @JsonProperty("htmlBodyBack") String htmlBodyBack) {
        this.name = name;
        this.fields = fields;
        this.sortField = sortField;
        this.css = css;
        this.htmlBodyFront = htmlBodyFront;
        this.htmlBodyBack = htmlBodyBack;
        this.totalFieldCount = fields.length;
        updateHtmlFront();
        updateHtmlBack();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equals("") || name == null) {
            JOptionPane.showMessageDialog(null, "Card type name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.name = name;
    }

    public Field[] getFields() {
        return fields;
    }

    @JsonIgnore
    public String[] getFieldNames() {
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }
        return names;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    private boolean fieldExists(String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addField(Field field) {
        if (fieldExists(field.getName())) {
            JOptionPane.showMessageDialog(null, "A field with that name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fields.length == 0) {
            sortField = field;
        }
        Field[] newFields = new Field[fields.length + 1];
        for (int i = 0; i < fields.length; i++) {
            newFields[i] = fields[i];
        }
        newFields[fields.length] = field;
        fields = newFields;
    }

    public void addField(String name) {
        if (fieldExists(name)) {
            JOptionPane.showMessageDialog(null, "A field with that name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fields.length == 0) {
            sortField = new Field(name);
        }
        Field[] newFields = new Field[fields.length + 1];
        for (int i = 0; i < fields.length; i++) {
            newFields[i] = fields[i];
        }
        newFields[fields.length] = new Field(name);
        fields = newFields;
    }

    public void removeField(String name) {
        if (fields.length < 3) {
            // least amount of fields a card can have is 2
            JOptionPane.showMessageDialog(null, "Cannot remove field. Card must have at least two fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Field[] newFields = new Field[fields.length - 1];
        if (sortField.getName().equals(name)) {
            sortField = null;
            throw new IllegalArgumentException("Cannot remove the sort field.");
        }
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equals(name)) {
                newFields[index] = fields[i];
                index++;
            }
        }
        fields = newFields;
    }

    public void renameField(String oldName, String newName) {
        if (fieldExists(newName)) {
            JOptionPane.showMessageDialog(null, "A field with that name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Field field : fields) {
            if (field.getName().equals(oldName)) {
                field.setName(newName);
            }
        }
    }

    public void setSortField(String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                sortField = field;
            }
        }
    }

    public Field getSortField() {
        return sortField;
    }

    public int getTotalFieldCount() {
        return totalFieldCount;
    }

    public String getHtmlFront() {
        return htmlFront;
    }

    public String getHtmlBack() {
        return htmlBack;
    }


    /**
     * Sets the HTML for the front side of the card type.
     */
    public void updateHtmlFront() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>Card</title>");
        sb.append("<style>");
        sb.append(css);
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(htmlBodyFront);
        sb.append("</body>");
        sb.append("</html>");
        this.htmlFront = sb.toString();
    }

    /**
     * Sets the HTML for the back side of the card type.
     */
    public void updateHtmlBack() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>Card</title>");
        sb.append("<style>");
        sb.append(css);
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(htmlBodyBack);
        sb.append("</body>");
        sb.append("</html>");
        this.htmlBack = sb.toString();

    }

    

    public String getHtmlBodyFront() {
        return htmlBodyFront;
    }

    public void setHtmlBodyFront(String htmlBodyFront) {
        this.htmlBodyFront = htmlBodyFront;
        updateHtmlFront();
    }

    public String getHtmlBodyBack() {
        return htmlBodyBack;
    }

    public void setHtmlBodyBack(String htmlBodyBack) {
        this.htmlBodyBack = htmlBodyBack;
        updateHtmlBack();
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
        updateHtmlFront();
        updateHtmlBack();
    }

    @Override
    public String toString() {
        return "CardType{" +
                "name='" + name + '\'' +
                ", fields=" + Arrays.toString(fields) +
                ", totalFieldCount=" + totalFieldCount +
                ", htmlFront='" + htmlFront + '\'' +
                ", htmlBack='" + htmlBack + '\'' +
                ", css='" + css + '\'' +
                ", htmlBodyFront='" + htmlBodyFront + '\'' +
                ", htmlBodyBack='" + htmlBodyBack + '\'' +
                '}';
    }
}
