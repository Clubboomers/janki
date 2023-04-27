package main.cardeditor;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;

class ImageTransferHandler extends TransferHandler {
    private JTextPane textPane;

    public ImageTransferHandler(JTextPane textPane) {
        this.textPane = textPane;
    }
    @Override
    public boolean canImport(TransferSupport support) {
        // Check if the transferable data contains an image
        return support.isDataFlavorSupported(DataFlavor.imageFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            // Get the transferred image data
            Transferable transferable = support.getTransferable();
            BufferedImage image = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);

            // Insert the image into the text pane
            Style style = textPane.getStyledDocument().getStyle(StyleContext.DEFAULT_STYLE);
            ImageIcon icon = new ImageIcon(image);
            textPane.insertIcon(icon);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}