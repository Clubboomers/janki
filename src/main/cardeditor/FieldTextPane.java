package main.cardeditor;

import main.utility.MediaTextReplacer;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;

public class FieldTextPane extends JTextPane {
    private String fieldName;
    private MediaTextReplacer mediaTextReplacer;
    private HTMLDocument doc;
    public FieldTextPane(String fieldName) {
        super();
        this.fieldName = fieldName;
        setContentType("text/html");
        setEditorKit(new TextPaneEditorKit());
        this.setTransferHandler(new ImageStringTransferHandler(this));
        addMouseListener(new CardEditorMouseListener(this));

        ((AbstractDocument)this.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // called when text is inserted into the document
                // delegate to the FilterBypass to insert the text into the document
                fb.insertString(offset, string, attr);

            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                // called when text is removed from the document
                // delegate to the FilterBypass to remove the text from the document
                fb.remove(offset, length);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // called when text is replaced in the document
                // check if the inserted text is an image from the clipboard
                fb.replace(offset, length, text, attrs);
            }
        });
    }

    /**
     * Text formats weirdly when using JTextPane and adds tags like <body> and <p>.
     * This method removes the tags and returns the text in plain text.
     * @return text in field
     */
    @Override
    public String getText() {
        // get text between <body> tags
        String text = super.getText();
        int start = text.indexOf("<body>") + 6;
        int end = text.lastIndexOf("</body>");
        text = text.substring(start, end).trim();
        if (text.startsWith("<p") && text.endsWith("</p>")) {
            // get text between <p> tags
            start = text.indexOf(">") + 1;
            end = text.lastIndexOf("<");
            text = text.substring(start, end).trim();
        }
        return text.trim();
    }

    public void insertTag(String tag) {
        // insert the tag into the document at the caret position
        setText(getText() + tag);
        //mediaTextReplacer.update();
    }

    public boolean hasText() {
        return getText().length() > 0;
    }

    public String getFieldName() {
        return fieldName;
    }
}
