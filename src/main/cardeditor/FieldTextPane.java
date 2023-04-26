package main.cardeditor;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.text.html.HTML;

public class FieldTextPane extends JTextPane {
    String fieldName;
    public FieldTextPane(String fieldName) {
        super();
        this.fieldName = fieldName;
        setEditorKit(new TextPaneEditorKit());
        setContentType("text/html");
    }

    @Override
    public String getText() {
        // get text in <body> tag
        String text = super.getText();
        int start = text.indexOf("<body>") + 6;
        int end = text.lastIndexOf("</body>");
        return text.substring(start, end);
    }

    public String getFieldName() {
        return fieldName;
    }
}
