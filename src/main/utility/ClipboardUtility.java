package main.utility;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;

public class ClipboardUtility {
    public ClipboardUtility() {
    }

    public static boolean isImageInClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            return true;
        }
        return false;
    }

    public static BufferedImage getImageFromClipboard() {
        BufferedImage image = null;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (isImageInClipboard()) {
            try {
                image = (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }
        return null;
    }
}
