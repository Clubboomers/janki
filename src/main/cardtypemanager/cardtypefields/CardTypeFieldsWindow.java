package main.cardtypemanager.cardtypefields;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import main.cardtypemanager.CardTypeManagerWindow;
import main.data.CardType;
import main.mainwindow.MainWindow;

public class CardTypeFieldsWindow extends JDialog {
    public CardTypeFieldsWindow(MainWindow mw, CardTypeManagerWindow parent, CardType cardType) {
        super(parent, "Manage card type fields", true);
        this.setSize(300, 200);
        this.setLocationRelativeTo(mw);
        setResizable(false);

        CardTypeFieldsView view = new CardTypeFieldsView(mw, this, cardType);
        // Add 10 pixel inner padding to the view
        view.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(view);
        this.pack();
        this.setVisible(true);
    }
    
}
