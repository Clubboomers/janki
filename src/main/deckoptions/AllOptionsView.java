package main.deckoptions;

import main.data.Deck;
import main.data.Option;

import javax.swing.*;
import java.util.ArrayList;

public class AllOptionsView extends JScrollPane {
    private JPanel scrollPanePanel;
    private ArrayList<Option> deckOptions;
    private ArrayList<OptionPanel> optionPanels;
    public AllOptionsView(Deck deck) {
        super();
        deckOptions = deck.getOptions().getOptions();
        optionPanels = new ArrayList<>();
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new BoxLayout(scrollPanePanel, BoxLayout.Y_AXIS));
        setViewportView(scrollPanePanel);

        for (Option option : deckOptions) {
            OptionPanel optionPanel = new OptionPanel(option);
            optionPanels.add(optionPanel);
            scrollPanePanel.add(optionPanel);
        }
    }

    public void applyOptions() {
        // apply the options to the deck
        for (Option option : deckOptions) {
            String name = option.getName();
            for (OptionPanel optionPanel : optionPanels) {
                if (optionPanel.getName().equals(name)) {
                    option.setValue(optionPanel.getValue());
                }
            }
        }
    }


}
