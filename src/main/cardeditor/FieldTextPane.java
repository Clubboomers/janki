package main.cardeditor;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FieldTextPane extends JTextPane {
    String fieldName;
    public FieldTextPane(String fieldName) {
        super();
        this.fieldName = fieldName;
        setContentType("text/plain");
        setEditorKit(new TextPaneEditorKit());
        this.setTransferHandler(new ImageTransferHandler(this));
    }

    /*@Override
    public String getText() {
        // get text between <body> tags
        String text = super.getText();
        int start = text.indexOf("<body>") + 6;
        int end = text.lastIndexOf("</body>");
        return text.substring(start, end);
    }*/

    public String getFieldName() {
        return fieldName;
    }
}
