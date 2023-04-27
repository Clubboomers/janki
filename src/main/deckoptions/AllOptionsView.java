package main.deckoptions;

import main.data.Deck;
import main.data.Option;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AllOptionsView extends JScrollPane {
    private JPanel scrollPanePanel;
    private ArrayList<Option> deckOptions;
    public AllOptionsView(Deck deck) {
        super();
        deckOptions = deck.getOptions().getOptions();
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));
        setViewportView(scrollPanePanel);

        for (Option option : deckOptions) {
            OptionItem optionItem = new OptionItem(option);
            scrollPanePanel.add(optionItem);
        }
    }
}
