package main.cardeditor;

import main.utility.ClipboardUtility;
import main.utility.ImageUtility;
import main.utility.MediaUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.io.ByteArrayInputStream;
import java.io.File;

public class FieldTextPane extends JTextPane {
    String fieldName;
    public FieldTextPane(String fieldName) {
        super();
        this.fieldName = fieldName;
        setContentType("text/plain");
        setEditorKit(new TextPaneEditorKit());
        this.setTransferHandler(new ImageTransferHandler(this));
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

            private boolean textIsImage(String text) {
                // check if the text is an image from the clipboard
                // you can use the ImageIO class to check if the text is an image
                // for example, you can use ImageIO.read(new ByteArrayInputStream(text.getBytes())) != null
                // to check if the text is a valid image
                // return true if the text is an image, false otherwise
                System.out.println("Checking if text is image...");
                try {
                    ImageIO.read(new ByteArrayInputStream(text.getBytes()));
                    System.out.println("Text is image!");
                    return true;
                } catch (Exception e) {
                    System.out.println("Text is not image!");
                    return false;
                }
            }

            private File createImageFile(String text) {
                // create a local file for the image
                // you can use the ImageIO class to write the image to a file
                // for example, you can use ImageIO.write(ImageIO.read(new ByteArrayInputStream(text.getBytes())), "png", file)
                // to write the image to a PNG file
                // return the created file
                System.out.println("Creating image file...");
                MediaUtility mediaUtility = new MediaUtility();
                String filePath = mediaUtility.saveImageFromClipboard();
                File file = new File(filePath);
                return file;
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

    public boolean hasText() {
        return getText().length() > 0;
    }

    public String getFieldName() {
        return fieldName;
    }
}
