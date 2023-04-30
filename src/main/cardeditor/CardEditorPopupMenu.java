package main.cardeditor;

import main.utility.MediaUtility;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class CardEditorPopupMenu extends JPopupMenu {
    public CardEditorPopupMenu(FieldTextPane fieldTextPane) {
        super();
        /**
         * create media option that shows more options on hover
         * media option should have a sub menu with the following options:
         * - image
         * - audio
         */
        JMenu media = new JMenu("Media");
            JMenuItem image = new JMenuItem("Add Image");
            JMenuItem audio = new JMenuItem("Add Audio");
            media.add(image);
            media.add(audio);
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem selectAll = new JMenuItem("Select All");
        add(media);
        add(cut);
        add(copy);
        add(paste);
        add(delete);
        add(selectAll);

        image.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // only allow image files
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || MediaUtility.isImage(f);
                }

                @Override
                public String getDescription() {
                    return MediaUtility.IMAGE_FILE_DESCRIPTION;
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(fieldTextPane);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                MediaUtility mediaUtility = new MediaUtility();
                Image image1 = new ImageIcon(mediaUtility.saveImageFromFile(selectedFile)).getImage();
                // insert img tag into text pane
                fieldTextPane.insertIcon(new ImageIcon(image1));
            }
        });
    }
}
