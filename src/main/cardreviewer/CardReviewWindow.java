package main.cardreviewer;

import javax.swing.*;

import main.data.Deck;
import main.mainwindow.MainWindow;

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
