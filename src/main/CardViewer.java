package main;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.data.Card;
import main.data.CardType;
import main.utility.MediaTextReplacer;

public class CardViewer extends JPanel { // TODO: make this a JFXPanel and use JavaFX WebView instead of JEditorPane
    private JEditorPane editorPane;
    private CardType cardType;
    private Card card;
    private String[] fieldNames;
    private String html;
    private Pattern pattern = Pattern.compile("\\{\\{([^}]*)\\}\\}"); // checks for {{...}} e.g. {{field_name}}
    private MediaTextReplacer mediaTextReplacer;
    private JScrollPane scrollPane;
    private WebView webView;
    private WebEngine webEngine;
    private JFXPanel jfxPanel;
    public CardViewer(CardType cardType, String html) {
        super(new BorderLayout());
        this.cardType = cardType;
        this.html = html;
        this.fieldNames = cardType.getFieldNames();
        scrollPane = new JScrollPane();
        mediaTextReplacer = new MediaTextReplacer();
        html = cardTypePreviewFieldReplacer(html);
        html = mediaTextReplacer.update(html);
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            // TODO: handle exception
            editorPane.setText(e.getMessage());
        }
        editorPane.setEditable(false);
        // make caret invisible for the editor pane
        editorPane.setCaretColor(new Color(0, 0, 0, 0));
        scrollPane.setViewportView(editorPane);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public CardViewer(Card card, CardType cardType, String html) {
        super(new BorderLayout());
        this.card = card;
        this.cardType = cardType;
        this.html = html;
        this.fieldNames = cardType.getFieldNames();
        scrollPane = new JScrollPane();
        mediaTextReplacer = new MediaTextReplacer();
        html = cardReviewerFieldReplace(html);
        html = mediaTextReplacer.update(html);
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            // TODO: handle exception
            editorPane.setText(e.getMessage());
        }
        editorPane.setEditable(false);
        // make caret invisible for the editor pane
        editorPane.setCaretColor(new Color(0, 0, 0, 0));
        scrollPane.setViewportView(editorPane);
        this.add(scrollPane, BorderLayout.CENTER);
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
                    html = html.replace("{{" + fieldName + "}}", card.getFieldValue(fieldName).toString());
                }
            }
        }
        return html;
    }

    public void updateHtml(String testHtml) {
        html = cardTypePreviewFieldReplacer(testHtml);
        html = mediaTextReplacer.update(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setHtmlFront() {
        html = cardType.getHtmlFront();
        html = cardReviewerFieldReplace(html);
        html = mediaTextReplacer.update(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setHtmlBack() {
        html = cardType.getHtmlBack();
        html = cardReviewerFieldReplace(html);
        html = mediaTextReplacer.update(html);
        try {
            editorPane.setText(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}