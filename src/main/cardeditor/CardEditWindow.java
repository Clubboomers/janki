package main.cardeditor;

import main.data.Card;
import main.mainwindow.MainWindow;

import javax.swing.*;

public class CardEditWindow extends JFrame {
    public CardEditWindow(MainWindow mw, JFrame parent, Card card) {
        super("Card Editor");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        CardEditView cardEditView = new CardEditView(this, mw, card);
        // add 5 px padding
        cardEditView.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentPane(cardEditView);
        setVisible(true);
    }
}
