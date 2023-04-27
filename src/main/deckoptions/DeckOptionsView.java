package main.deckoptions;

import main.data.Deck;

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
        }
}
