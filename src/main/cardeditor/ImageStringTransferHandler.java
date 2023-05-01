package main.cardeditor;

import main.utility.MediaUtility;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;

class ImageStringTransferHandler extends TransferHandler {
    private FieldTextPane fieldTextPane;

    public ImageStringTransferHandler(FieldTextPane fieldTextPane) {
        this.fieldTextPane = fieldTextPane;
    }
    @Override
    public boolean canImport(TransferSupport support) {
        // Check if the transferable data contains an image or a string
        return support.isDataFlavorSupported(DataFlavor.imageFlavor) ||
                support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            // Get the transferred image data
            Transferable transferable = support.getTransferable();
            // check what type of data is being transferred
            if (transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                // check if image extension is supported (MediaUtility.isImageExtensionSupported)
                BufferedImage image = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
                MediaUtility temp = new MediaUtility();
                fieldTextPane.insertTag(temp.saveImageFromBufferedImage(image));
            } else if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                StyledDocument doc = fieldTextPane.getStyledDocument();
                int caretPosition = fieldTextPane.getCaretPosition();
                try {
                    doc.insertString(caretPosition, text, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}