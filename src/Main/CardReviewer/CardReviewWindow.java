package Main.CardReviewer;

import javax.swing.*;

import Main.Data.Deck;
import Main.MW.MainWindow;

public class CardReviewWindow extends JFrame {
    public CardReviewWindow(MainWindow mw, Deck deckToReview) {
        super("Card Review");
        setSize(500, 500);
        setLocationRelativeTo(mw);
        CardReviewView view = new CardReviewView(mw, deckToReview);
        this.setContentPane(view);
        pack();
        setVisible(true);
    }
}
