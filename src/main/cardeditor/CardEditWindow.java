package main.cardeditor;

import main.data.Card;
import main.mainwindow.MainWindow;

import javax.swing.*;

public class CardEditWindow extends JFrame {
    public CardEditWindow(MainWindow mw, Card card) {
        super("Card Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        CardEditView cardEditView = new CardEditView(this, mw, card);
        // add 10px padding
        cardEditView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(cardEditView);
        setVisible(true);
    }
}
