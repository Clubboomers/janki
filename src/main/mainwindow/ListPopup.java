package main.mainwindow;

import javax.swing.*;

public class ListPopup extends JPopupMenu {
    /**
     * Lets user manage decks by renaming or removing them
     * @param mw main window, contains wanted deck list
     */
    public ListPopup(MainWindow mw) {
        super();
        JMenuItem renameMenuItem = new JMenuItem("Rename");
        renameMenuItem.addActionListener(e -> {
            String deckName = MainContentView.getSelectedDeck();
            String newName = JOptionPane.showInputDialog(null, "Enter new name for deck " + "\"" + deckName + "\"", "Rename deck", JOptionPane.PLAIN_MESSAGE);
            if (newName != null) {
                mw.renameDeckWithName(deckName, newName);
                System.out.println("Renamed deck " + "\"" + deckName + "\"" + " to " + "\"" + newName + "\"");
                mw.updateView();
            }
        });
        add(renameMenuItem);

        JMenuItem optionsMenuItem = new JMenuItem("Options");
        optionsMenuItem.addActionListener(e -> {
            String deckName = MainContentView.getSelectedDeck();
            mw.deckOptions(mw.getDeckWithName(deckName));
        });
        add(optionsMenuItem);

        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(e -> {
            String deckName = MainContentView.getSelectedDeck();
            mw.removeDeckWithName(deckName);
            System.out.println("Removed deck " + "\"" + deckName + "\"");
            mw.updateView();
        });
        add(deleteMenuItem);

    }
}
