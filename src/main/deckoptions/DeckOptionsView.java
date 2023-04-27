package main.deckoptions;

import main.data.Deck;
import main.helper.OkCancelButtonsPanel;

import javax.swing.*;

public class DeckOptionsView extends JPanel {
        private DeckOptionsWindow parent;
        private Deck deck;
        public DeckOptionsView(DeckOptionsWindow parent, Deck deck) {
            super();
            this.parent = parent;
            this.deck = deck;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(new JLabel("Deck Options"));
            add(new AllOptionsView(deck));
            // add 5 px padding
            add(Box.createVerticalStrut(5));
            add(new OkCancelButtonsPanel() {
                @Override
                public void btnOk() {
                    parent.dispose();
                }

                @Override
                public void btnCancel() {
                    parent.dispose();
                }
            });
        }
}
