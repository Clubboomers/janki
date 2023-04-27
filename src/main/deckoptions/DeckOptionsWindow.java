package main.deckoptions;

import main.data.Deck;

import javax.swing.*;

public class DeckOptionsWindow extends JDialog {

        /**
        * Deck options window
        */
        public DeckOptionsWindow(Deck deck) {
            super();
            setTitle("Deck Options");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 400);
            DeckOptionsView deckOptionsView = new DeckOptionsView(this, deck);
            this.setContentPane(deckOptionsView);
            setVisible(true);
        }
}
