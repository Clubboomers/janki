package main.deckoptions;

import main.data.Deck;
import main.data.DeckOptions;

import javax.swing.*;

public class AllOptionsView extends JScrollPane {
    private JPanel scrollPanePanel;
    private DeckOptions deckOptions;
    public AllOptionsView(Deck deck) {
        super();
        deckOptions = deck.getOptions();
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));
        setViewportView(scrollPanePanel);

        init();
    }

    private void init() {
    }
}
