package main;

import java.awt.BorderLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import main.data.Card;
import main.data.CardType;

public class CardViewer extends JPanel {
    private JEditorPane editorPane;
    private CardType cardType;
    private Card card;
    private String[] fieldNames;
    private String html;
    private Pattern pattern = Pattern.compile("\\{\\{([^}]*)\\}\\}"); // {{field_name}}
    public CardViewer(CardType cardType, String html) {
        super(new BorderLayout());
        this.cardType = cardType;
        this.html = html;
        this.fieldNames = cardType.getFieldNames();
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        // remove caret
        html = cardTypePreviewFieldReplacer(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            // TODO: handle exception
            editorPane.setText(e.getMessage());
        }
        editorPane.setEditable(false);
        this.add(editorPane, BorderLayout.CENTER);
    }

    public CardViewer(Card card, String html) {
        super(new BorderLayout());
        this.card = card;
        this.cardType = card.getCardType();
        this.html = html;
        this.fieldNames = cardType.getFieldNames();
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        html = cardReviewerFieldReplace(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            // TODO: handle exception
            editorPane.setText(e.getMessage());
        }
        editorPane.setEditable(false);
        this.add(editorPane, BorderLayout.CENTER);
    }

    private String cardTypePreviewFieldReplacer(String html) {
        // find all the instances of {{...}} in the html and, if what is inside is a field name, replace it with the name of the field
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String fieldName = matcher.group(1);
            for (String name : fieldNames) {
                if (name.equals(fieldName)) {
                    html = html.replace("{{" + fieldName + "}}", "(" + name + ")");
                }
            }
        }
        return html;
    }

    private String cardReviewerFieldReplace(String html) {
        // find all the instances of {{...}} in the html and, if what is inside is a field name, replace it with the value of the field
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String fieldName = matcher.group(1);
            for (String name : fieldNames) {
                if (name.equals(fieldName)) {
                    System.out.println(fieldName);
                    html = html.replace("{{" + fieldName + "}}", card.getFieldValue(fieldName).toString());
                }
            }
        }
        return html;
    }

    public void updateHtml(String testHtml) {
        html = cardTypePreviewFieldReplacer(testHtml);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            // TODO: handle exception
            editorPane.setText(e.getMessage());
        }
        /*Platform.runLater(() -> {
            webEngine.loadContent(html);
        });*/
    }

    public void setHtmlFront() {
        html = cardType.getHtmlFront();
        html = cardReviewerFieldReplace(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setHtmlBack() {
        html = cardType.getHtmlBack();
        html = cardReviewerFieldReplace(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}